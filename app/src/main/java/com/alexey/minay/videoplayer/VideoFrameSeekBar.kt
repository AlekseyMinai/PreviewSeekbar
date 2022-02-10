package com.alexey.minay.videoplayer

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.alexey.minay.videoplayer.databinding.LayVideoFrameSeekbarBinding
import com.alexey.minay.videoplayer.utils.TimeUtils
import com.alexey.minay.videoplayer.utils.setOnSeekBarChangeListener

class VideoFrameSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mCanUpdate: Boolean = true
    private var mLastTotalValue: Long = 0
    private var mDuration: Long = 0
    private val mMaxProgress get() = mBinding.seekBar.max

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

    private fun setVideoFrameFor(timeUs: Long) {
        val frameWidth = resources.getDimensionPixelSize(R.dimen.frame_width)
        val frameHeight = resources.getDimensionPixelSize(R.dimen.frame_height)
        mGetFrame(timeUs, frameWidth, frameHeight)
    }

    var url: String? = null

    fun update(progressMs: Long, durationMs: Long) {
        if (durationMs < 0) return
        mDuration = durationMs
        if (!mCanUpdate) return
        mLastTotalValue = durationMs
        mBinding.progress.text = TimeUtils.msToTime(progressMs)
        mBinding.total.text = TimeUtils.msToTime(durationMs)
        mBinding.seekBar.progress = (progressMs * 1000 / durationMs).toInt()
    }

    private var mGetFrame: (timeUs: Long, width: Int, height: Int) -> Unit = { _, _, _ -> }

    fun setFrameProvider(getFrame: (timeUs: Long, width: Int, height: Int) -> Unit) {
        mGetFrame = getFrame
    }

    fun setVideoFrame(bitmap: Bitmap) {
        mBinding.image.setImageBitmap(bitmap)
    }

}