package com.alexey.minay.videoplayer

import android.animation.ValueAnimator
import android.view.ScaleGestureDetector
import android.view.View
import com.alexey.minay.videoplayer.utils.isPortrait

class VideoScaleDetector(
    private val player: View,
    private val root: View,
    private val gestureInterceptor: View
) : ScaleGestureDetector.OnScaleGestureListener {

    private var mTotalScale = DEFAULT_SCALE
    private var mFrameValueAnimator: ValueAnimator? = null
    private var mPlayerValueAnimator: ValueAnimator? = null

    override fun onScale(p0: ScaleGestureDetector): Boolean {
        val playerHeight = player.height
        val playerWidth = player.width
        val screenHeight = root.height
        val screenWidth = root.width

        var newScale = mTotalScale * p0.scaleFactor

        if (player.context.isPortrait()) {
            if (playerHeight < playerWidth) {
                return false
            }

            if (playerHeight * newScale >= screenHeight) {
                newScale = screenHeight.toFloat() / playerHeight
            }

        } else {
            if (playerWidth < playerHeight) {
                return false
            }

            if (playerWidth * newScale >= screenWidth) {
                newScale = screenWidth.toFloat() / playerWidth
            }
        }

        if (newScale < 1) {
            newScale = DEFAULT_SCALE
        }

        mTotalScale = newScale
        player.scaleX = mTotalScale
        player.scaleY = mTotalScale
        return true
    }

    override fun onScaleBegin(p0: ScaleGestureDetector): Boolean {
        animateFrame(0f, 0.4f)

        player.pivotX = root.width.toFloat() / 2
        player.pivotY = root.height.toFloat() / 2
        return true
    }

    override fun onScaleEnd(p0: ScaleGestureDetector) {
        when (mTotalScale) {
            DEFAULT_SCALE -> animateFrame(0.4f, 0f)
            else -> animateFrame(0.4f, 0.6f, 0.4f, 0f)
        }
        anim()
    }

    private fun animateFrame(vararg values: Float) {
        mFrameValueAnimator?.cancel()
        mFrameValueAnimator = ValueAnimator.ofFloat(*values)
        mFrameValueAnimator?.addUpdateListener { valueAnimation ->
            gestureInterceptor.alpha = valueAnimation.animatedValue as Float
        }
        mFrameValueAnimator?.start()
    }

    private fun anim() {
        val playerHeight = player.height
        val playerWidth = player.width
        val screenHeight = root.height
        val screenWidth = root.width
        if (player.context.isPortrait()) {

        } else {
            val maxScale = screenWidth.toFloat() / playerWidth
            if (maxScale - mTotalScale < (maxScale - DEFAULT_SCALE) / 2) {
                animatePlayer(mTotalScale, maxScale)
            } else {
                animatePlayer(mTotalScale, DEFAULT_SCALE)
            }
        }
    }

    private fun animatePlayer(vararg values: Float) {
        mPlayerValueAnimator?.cancel()
        mPlayerValueAnimator = ValueAnimator.ofFloat(*values)
        mPlayerValueAnimator?.duration = 100
        mPlayerValueAnimator?.addUpdateListener { valueAnimation ->
            mTotalScale = valueAnimation.animatedValue as Float
            player.scaleX = mTotalScale
            player.scaleY = mTotalScale
        }
        mPlayerValueAnimator?.start()
    }

    companion object {
        private const val DEFAULT_SCALE = 1.0f
    }

}