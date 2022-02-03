package com.alexey.minay.videoplayer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.alexey.minay.videoplayer.databinding.LayVideoFrameSeekbarBinding

class VideoFrameSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mCanUpdate: Boolean = true
    private var mLastTotalValue: Long = 0
    private val mFrameWidth by lazy { resources.getDimensionPixelSize(R.dimen.frame_width) }

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
                    mBinding.seekProgress.text = progress.toString()
                    setSeekGroupPosition(progress)
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

        val maxProgress = mBinding.seekBar.max

        val minSeekFrameValue =
            ((frameWidth.toFloat() / 2) - progressWidth - margin - seekbarPadding) /
                    seekBarWidth * maxProgress
        val maxSeekFrameValue =
            (1 - ((frameWidth.toFloat() / 2) - totalTimeWidth - margin - seekbarPadding) /
                    seekBarWidth) * maxProgress

        val params = mBinding.image.layoutParams as ConstraintLayout.LayoutParams

        val seekProgress =
            progress.coerceIn(minSeekFrameValue.toInt() + 1, maxSeekFrameValue.toInt())

        val bias =
            (seekProgress.toFloat() - minSeekFrameValue) / (maxSeekFrameValue - minSeekFrameValue)

        params.horizontalBias = bias
        mBinding.image.layoutParams = params
    }

    fun update(progressMs: Long, totalMs: Long) {
        mLastTotalValue = totalMs
        mBinding.progress.text = progressMs.toString()
        mBinding.total.text = totalMs.toString()
        mBinding.seekBar.progress = (progressMs * 1000 / totalMs).toInt()
    }

}