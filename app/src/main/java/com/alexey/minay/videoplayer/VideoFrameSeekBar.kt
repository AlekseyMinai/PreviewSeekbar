package com.alexey.minay.videoplayer

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.alexey.minay.videoplayer.databinding.LayVideoFrameSeekbarBinding
import com.alexey.minay.videoplayer.utils.TimeUtils
import com.alexey.minay.videoplayer.utils.setOnSeekBarChangeListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class VideoFrameSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mCanUpdate: Boolean = true
    private var mLastTotalValue: Long = 0
    private var mDuration: Long = 0
    private val mMaxProgress get() = mBinding.seekBar.max

    private val mFrameWidth = resources.getDimensionPixelSize(R.dimen.frame_width) / 3
    private val mFrameHeight = resources.getDimensionPixelSize(R.dimen.frame_height) / 3

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

    private var mLastNormalized: Long? = null

    private fun loadBitmap(url: String, timeUs: Long, width: Int, height: Int, canSet: Boolean) {
        val options: RequestOptions = RequestOptions().frame(timeUs * 1000)
            .override(width, height)

        val previous = mBinding.image.drawable

        Glide.with(context).asBitmap()
            .load(url)
            .placeholder(previous)
            .centerCrop()
            .apply(options)
            .apply {
                when {
                    canSet -> into(mBinding.image)
                    else -> preload()
                }
            }

    }

    private fun preload(durationMs: Long) {
        for (i in 0..durationMs) {
            setVideoFrameFor(i, false)
        }
    }

    private fun setVideoFrameFor(timeUs: Long, canSet: Boolean = true) {
        val url ="https://rr11---sn-n8v7kn7l.googlevideo.com/videoplayback?expire=1646188968&ei=SIUeYoThIeOrxN8P7OaXkAM&ip=185.108.106.210&id=o-AP3PWwkWe2pU2BfYY2Xfyh-Z2mKBgx-6i7Lxpq_BpO56&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=RJDG4tN8vNxTMSg7vcUiEC8G&gir=yes&clen=148693097&ratebypass=yes&dur=2012.437&lmt=1645850678380880&fexp=24001373,24007246,24162927&c=WEB&txp=4430434&n=icKd1GdSKd0GYA&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIgQhoGR32bcRDCD974VOrXlPz2831ZPtVt5YHq-bzhM8ECIQCCQuNxbK-o7ULIzX2LQsX5jRlXWkUQqlTd5AhXM5G73A%3D%3D&redirect_counter=1&rm=sn-5hness7l&req_id=c3885de10576a3ee&cms_redirect=yes&cmsv=e&ipbypass=yes&mh=s-&mip=136.169.211.3&mm=31&mn=sn-n8v7kn7l&ms=au&mt=1646167259&mv=m&mvi=11&pl=24&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRgIhAI82cxiH-ySZPoh4WO9d5FVzI-sRJz6aYM2s3agFhFMWAiEAo3K8JhDq1wNzEmlmTTQemH-r0nIuufA-twY3MrjzhL4%3D"

        val coefficient = when {
            mDuration < 30000 -> 1
            else -> mDuration / 30
        }

        val normalized = timeUs - timeUs % coefficient

        if (normalized == mLastNormalized) return

        mLastNormalized = normalized

        Log.d("setVideoFrameFor", "setVideoFrameFor $timeUs $normalized $coefficient")

        loadBitmap(url, normalized, mFrameWidth, mFrameHeight, canSet)
    }

    fun update(progressMs: Long, durationMs: Long) {
        if (durationMs < 0) return

        //temp
        if (mDuration == 0L) {
            GlobalScope.launch {
                preload(durationMs * 1000)
            }
        }

        mDuration = durationMs
        if (!mCanUpdate) return
        mLastTotalValue = durationMs
        mBinding.progress.text = TimeUtils.msToTime(progressMs)
        mBinding.total.text = TimeUtils.msToTime(durationMs)
        mBinding.seekBar.progress = (progressMs * 1000 / durationMs).toInt()
    }

}