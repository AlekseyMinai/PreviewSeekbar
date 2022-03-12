package com.alexey.minay.previewseekbar.view

import android.content.Context
import com.alexey.minay.previewseekbar.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.CoroutineScope

class VideoPreviewLoader(
    private val coroutineScope: CoroutineScope,
    private val context: Context,
    private val url: String
) {

    private var mLastInterval: Long? = null
    private var mDuration: Long? = null
    private val mFrameWidth = context.resources.getDimensionPixelSize(R.dimen.frame_width) / 3
    private val mFrameHeight = context.resources.getDimensionPixelSize(R.dimen.frame_height) / 3

    private val mExoplayer by lazy { ExoPlayer.Builder(context).build() }
    private var mPlayerView: PlayerView? = null

    fun preload(durationMs: Long, playerView: PlayerView) {
        mDuration = durationMs
        mPlayerView = playerView
        mPlayerView?.player = mExoplayer

        val mediaItem = MediaItem.fromUri(url)
        mExoplayer.setMediaItem(mediaItem)
        mExoplayer.prepare()
    }

    fun load(timeUs: Long) {
        mDuration?.let {
            val interval = getInterval(timeUs, it)

            if (mLastInterval == interval) return

            mLastInterval = interval

            mExoplayer.seekTo(interval)
        }
    }

    private fun getInterval(timeMs: Long, durationMs: Long): Long {
        val coefficient = when {
            durationMs < 30000 -> 1
            else -> durationMs / 30
        }

        return timeMs - timeMs % coefficient
    }

}