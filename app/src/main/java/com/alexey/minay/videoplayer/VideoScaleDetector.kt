package com.alexey.minay.videoplayer

import android.animation.ValueAnimator
import android.view.ScaleGestureDetector
import android.view.View
import androidx.core.animation.addListener
import com.alexey.minay.videoplayer.utils.isPortrait

class VideoScaleDetector(
    private val player: View,
    private val root: View,
    private val gestureInterceptor: View
) : ScaleGestureDetector.OnScaleGestureListener {

    private var mTotalScale = DEFAULT_SCALE
    private var mIsStarted = false
    private var mValueAnimator: ValueAnimator? = null

    override fun onScale(p0: ScaleGestureDetector): Boolean {
        val playerHeight = player.height
        val playerWidth = player.width
        val screenHeight = root.height
        val screenWidth = root.width

        val newScale = mTotalScale * p0.scaleFactor

        if (player.context.isPortrait()) {
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

        mTotalScale = newScale
        player.scaleX = mTotalScale
        player.scaleY = mTotalScale
        return true
    }

    override fun onScaleBegin(p0: ScaleGestureDetector): Boolean {
        animate(0f, 0.4f)

        player.pivotX = root.width.toFloat() / 2
        player.pivotY = root.height.toFloat() / 2
        return true
    }

    override fun onScaleEnd(p0: ScaleGestureDetector) {
        when (mTotalScale) {
            DEFAULT_SCALE -> animate(0.4f, 0f)
            else -> animate(0.4f, 0.6f, 0.4f, 0f)
        }
    }

    private fun animate(vararg values: Float) {
        mValueAnimator?.cancel()
        mValueAnimator = ValueAnimator.ofFloat(*values)
        mValueAnimator?.addUpdateListener { valueAnimation ->
            gestureInterceptor.alpha = valueAnimation.animatedValue as Float
        }
        mValueAnimator?.addListener(onEnd = {
            mIsStarted = false
        })
        mValueAnimator?.start()
        mIsStarted = true
    }

    companion object {
        private const val DEFAULT_SCALE = 1.0f
    }

}