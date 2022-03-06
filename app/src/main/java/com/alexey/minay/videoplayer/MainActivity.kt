package com.alexey.minay.videoplayer

import android.os.Bundle
import android.view.ScaleGestureDetector
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alexey.minay.videoplayer.databinding.ActivityMainBinding
import com.alexey.minay.videoplayer.utils.isPortrait
import com.alexey.minay.videoplayer.view.VideoPreviewLoader
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
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        initGesture()
        setContentView(mBinding.root)
        initPlayer()
        initProgressChecker()
        initButtons()
        subscribeToViewMode()
    }

    private fun initGesture() {
        val detector =
            ScaleGestureDetector(this, object : ScaleGestureDetector.OnScaleGestureListener {

                var totalScale = 1f

                override fun onScale(p0: ScaleGestureDetector): Boolean {
                    val playerHeight = mBinding.player.height
                    val playerWidth = mBinding.player.width
                    val screenHeight = mBinding.root.height
                    val screenWidth = mBinding.root.width

                    val newScale = totalScale * p0.scaleFactor

                    if (isPortrait()) {
                        if (
                            playerHeight < playerWidth ||
                            playerHeight >= screenHeight ||
                            playerHeight * newScale > screenHeight
                        ) {
                            return false
                        }
                    } else {
                        if (
                            playerWidth < playerHeight ||
                            playerWidth >= screenWidth ||
                            playerWidth * newScale > screenWidth
                        ) return false
                    }

                    if (newScale < 1) return false

                    totalScale = newScale
                    mBinding.player.scaleX = totalScale
                    mBinding.player.scaleY = totalScale
                    return true
                }

                override fun onScaleBegin(p0: ScaleGestureDetector): Boolean {
                    mBinding.gestureInterceptor.setBackgroundResource(R.drawable.background_video_frame)
                    mBinding.player.pivotX = mBinding.root.width.toFloat() / 2
                    mBinding.player.pivotY = mBinding.root.height.toFloat() / 2
                    return true
                }

                override fun onScaleEnd(p0: ScaleGestureDetector) {
                    mBinding.gestureInterceptor.setBackgroundResource(0)
                }

            })

        mBinding.gestureInterceptor.setOnTouchListener { _, motionEvent ->
            detector.onTouchEvent(
                motionEvent
            )
        }
    }

    private fun initPlayer() {
        mBinding.player.player = mExoplayer
    }

    private fun initProgressChecker() {
        lifecycleScope.launchWhenStarted {
            val videoPreviewLoader = VideoPreviewLoader(
                coroutineScope = this,
                context = this@MainActivity,
                url = mViewModel.state.value.url
            )

            mBinding.videoFrameSeekBar.setVideoPreviewLoader(videoPreviewLoader)

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