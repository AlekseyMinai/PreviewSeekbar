package com.alexey.minay.videoplayer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alexey.minay.videoplayer.databinding.ActivityMainBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mViewModel by viewModels<VideoPlayerViewModel>()
    private val mExoplayer by lazy { ExoPlayer.Builder(this).build() }
    private var mLastUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initPlayer()
        initProgressChecker()
        initButtons()
        subscribeToViewMode()
//        val retriever = MediaMetadataRetriever()
//        retriever.setDataSource(this, Uri.parse(mViewModel.url))
//        val bitmap = retriever.getFrameAtTime(1000)

        //https://stackoverflow.com/questions/60655021/how-to-get-the-frame-of-video-at-specific-position-continuously-in-exoplayer
    }

    private fun initPlayer() {
        mBinding.player.player = mExoplayer
    }

    private fun initProgressChecker() {
        lifecycleScope.launchWhenStarted {
            while (isActive) {
                mBinding.videoFrameSeekBar.update(mExoplayer.currentPosition, mExoplayer.duration)
                delay(1000)
            }
        }
    }

    private fun initButtons() = with(mBinding) {
        play.setOnClickListener {
            mViewModel.changePlayingState()
        }
    }

    private fun subscribeToViewMode() {
        lifecycleScope.launchWhenStarted {
            mViewModel.state.collect(::render)
        }
    }

    private fun render(state: VideoPlayerState) {
        startVideo(state.url)
        renderButton(state.isPlaying)
    }

    private fun startVideo(url: String) {
        if (mLastUrl == url) return
        mLastUrl = url

        val mediaItem = MediaItem.fromUri(url)
        mExoplayer.setMediaItem(mediaItem)
        mExoplayer.prepare()
        mExoplayer.play()
    }

    private fun renderButton(isPlaying: Boolean) {
        mExoplayer.playWhenReady = isPlaying
        mBinding.play.setImageResource(
            when {
                isPlaying -> R.drawable.ic_pause
                else -> R.drawable.ic_play
            }
        )
    }

}