package com.alexey.minay.previewseekbar.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.alexey.minay.previewseekbar.R
import com.alexey.minay.previewseekbar.databinding.LayVideoFrameSeekbarBinding
import com.alexey.minay.previewseekbar.utils.TimeUtils
import com.alexey.minay.previewseekbar.utils.setOnSeekBarChangeListener
import java.lang.RuntimeException

class VideoFrameSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mCanUpdate: Boolean = true
    private var mLastTotalValue: Long = 0
    private var mDuration: Long = 0
    private val mMaxProgress get() = mBinding.seekBar.max
    private var mVideoPreviewLoader: VideoPreviewLoader? = null
    private val mBinding = LayVideoFrameSeekbarBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        mBinding.seekBar.setOnSeekBarChangeListener(
            onStartTrackingTouch = {
                mCanUpdate = false
                mBinding.seekGroup.isVisible = true
                mBinding.progressGroup.isInvisible = true
            },
            onStopTrackingTouch = {
                mCanUpdate = true
                mBinding.seekGroup.isVisible = false
                mBinding.progressGroup.isInvisible = false
            },
            onProgressChanged = { _, progress, isFromUser ->
                if (isFromUser) {
                    val seekProgress = progress * mDuration / mMaxProgress
                    mBinding.seekProgress.text = TimeUtils.msToTime(seekProgress)
                    setSeekGroupPosition(progress)
                    setVideoFrameFor(seekProgress)
                }
            }
        )
    }

    fun setVideoPreviewLoader(videoPreviewLoader: VideoPreviewLoader) {
        mVideoPreviewLoader = videoPreviewLoader
    }

    fun update(progressMs: Long, durationMs: Long) {
        if (durationMs < 0) return

        if (mDuration == 0L) {
            mVideoPreviewLoader ?: throw RuntimeException("Need to set previewLoader")
            mVideoPreviewLoader?.preload(durationMs)
        }

        mDuration = durationMs
        if (!mCanUpdate) return
        mLastTotalValue = durationMs
        mBinding.progress.text = TimeUtils.msToTime(progressMs)
        mBinding.total.text = TimeUtils.msToTime(durationMs)
        mBinding.seekBar.progress = (progressMs * 1000 / durationMs).toInt()
    }

    private fun setSeekGroupPosition(progress: Int) {
        val progressWidth = mBinding.progress.width
        val totalTimeWidth = mBinding.total.width
        val seekBarWidth = mBinding.seekBar.width
        val frameWidth = mBinding.image.width
        val margin = resources.getDimensionPixelSize(R.dimen.margin)
        val seekbarPadding = resources.getDimensionPixelSize(R.dimen.seekbar_padding)

        val minSeekFrameValue =
            ((frameWidth.toFloat() / 2) - progressWidth - margin - seekbarPadding) /
                    seekBarWidth * mMaxProgress
        val maxSeekFrameValue =
            (1 - ((frameWidth.toFloat() / 2) - totalTimeWidth - margin - seekbarPadding) /
                    seekBarWidth) * mMaxProgress

        val params = mBinding.image.layoutParams as ConstraintLayout.LayoutParams

        val seekProgress =
            progress.coerceIn(minSeekFrameValue.toInt() + 1, maxSeekFrameValue.toInt())

        val bias =
            (seekProgress.toFloat() - minSeekFrameValue) / (maxSeekFrameValue - minSeekFrameValue)

        params.horizontalBias = bias
        mBinding.image.layoutParams = params
    }

    private fun setVideoFrameFor(seekProgress: Long) {
        mVideoPreviewLoader?.load(seekProgress, mBinding.image)
    }

}