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

    private val mPlayerHeight get() = player.height
    private val mPlayerWidth get() = player.width
    private val mScreenHeight get() = root.height
    private val mScreenWidth get() = root.width

    override fun onScale(p0: ScaleGestureDetector): Boolean {
        var newScale = mTotalScale * p0.scaleFactor

        if (player.context.isPortrait()) {
            if (mPlayerHeight < mPlayerWidth) {
                return false
            }

            if (mPlayerHeight * newScale >= mScreenHeight) {
                newScale = mScreenHeight.toFloat() / mPlayerHeight
            }

        } else {
            if (mPlayerWidth < mPlayerHeight) {
                return false
            }

            if (mPlayerWidth * newScale >= mScreenWidth) {
                newScale = mScreenWidth.toFloat() / mPlayerWidth
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
        if (canNotScale()) {
            return false
        }

        animateFrame(0f, 0.4f)

        player.pivotX = root.width.toFloat() / 2
        player.pivotY = root.height.toFloat() / 2
        return true
    }

    override fun onScaleEnd(p0: ScaleGestureDetector) {
        if (canNotScale()) {
            return
        }

        when (mTotalScale) {
            DEFAULT_SCALE -> animateFrame(0.4f, 0f)
            else -> animateFrame(0.4f, 0.6f, 0.4f, 0f)
        }
        animateScale()
    }

    fun reset() {
        mTotalScale = DEFAULT_SCALE
        player.scaleX = mTotalScale
        player.scaleY = mTotalScale
    }

    fun dismiss() {
        mPlayerValueAnimator?.cancel()
        mFrameValueAnimator?.cancel()
    }

    private fun canNotScale(): Boolean {
        return if (player.context.isPortrait()) {
            mPlayerHeight < mPlayerWidth
        } else {
            mPlayerWidth < mPlayerHeight
        }
    }

    private fun animateFrame(vararg values: Float) {
        mFrameValueAnimator?.cancel()
        mFrameValueAnimator = ValueAnimator.ofFloat(*values)
        mFrameValueAnimator?.addUpdateListener { valueAnimation ->
            gestureInterceptor.alpha = valueAnimation.animatedValue as Float
        }
        mFrameValueAnimator?.start()
    }

    private fun animateScale() {
        fun changeScale(maxScale: Float) {
            if (maxScale - mTotalScale < (maxScale - DEFAULT_SCALE) / 2) {
                animatePlayer(mTotalScale, maxScale)
            } else {
                animatePlayer(mTotalScale, DEFAULT_SCALE)
            }
        }

        val maxScale = when {
            player.context.isPortrait() -> mScreenHeight.toFloat() / mPlayerHeight
            else -> mScreenWidth.toFloat() / mPlayerWidth
        }
        changeScale(maxScale)
    }

    private fun animatePlayer(vararg values: Float) {
        mPlayerValueAnimator?.cancel()
        mPlayerValueAnimator = ValueAnimator.ofFloat(*values)
        mPlayerValueAnimator?.duration = PLAYER_ANIMATE_DURATION
        mPlayerValueAnimator?.addUpdateListener { valueAnimation ->
            mTotalScale = valueAnimation.animatedValue as Float
            player.scaleX = mTotalScale
            player.scaleY = mTotalScale
        }
        mPlayerValueAnimator?.start()
    }

    companion object {
        private const val PLAYER_ANIMATE_DURATION = 100L
        private const val DEFAULT_SCALE = 1.0f
    }

}