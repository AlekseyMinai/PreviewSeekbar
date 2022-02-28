package com.alexey.minay.videoplayer

import android.content.Context
import android.graphics.Bitmap
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
import com.bumptech.glide.signature.ObjectKey


class VideoFrameSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mCanUpdate: Boolean = true
    private var mLastTotalValue: Long = 0
    private var mDuration: Long = 0
    private val mMaxProgress get() = mBinding.seekBar.max
    private var mGetFrame: (timeUs: Long, width: Int, height: Int) -> Unit = { _, _, _ -> }

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

    private fun loadBitmap(url: String, timeUs: Long) {
        val options: RequestOptions = RequestOptions().frame(timeUs * 1000)

        val previous = mBinding.image.drawable

        Glide.with(context).asBitmap()
            .load(url)
            .placeholder(previous)
            .centerCrop()
            //.signature(ObjectKey(timeUs))
            .apply(options)
            .into(mBinding.image)
    }

    private fun setVideoFrameFor(timeUs: Long) {
        val url =
            "https://rr5---sn-5hne6nz6.googlevideo.com/videoplayback?expire=1646099073&ei=ISYdYt-CCYTXx_APx6SI0Ag&ip=91.90.122.11&id=o-AGVY5_Yh1FTCVp-PepOQa2baNzb8G_W4Lf6tR91NiDHR&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=6FbGONfo9a9zOq6la4vlgWMG&gir=yes&clen=95781657&ratebypass=yes&dur=1693.152&lmt=1645633965252739&fexp=24001373,24007246&c=WEB&txp=5430434&n=pSzEunD-bfNUuA&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRAIgdX6_BZg4i9gtZ5UWTta_AGQoeLWC3H7GeNxthSkcUeMCIFC6f0LN1oX8pSJpub199yKDRLwa-i2wtv3ZYi_EBoAZ&redirect_counter=1&cm2rm=sn-1gie67e&req_id=232ab90b8760a3ee&cms_redirect=yes&cmsv=e&mh=HJ&mip=136.169.211.3&mm=34&mn=sn-5hne6nz6&ms=ltu&mt=1646077183&mv=u&mvi=5&pl=21&lsparams=mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRQIgKGtCeHjTdl6_hXdmDp44qZDqzzYJeZ72CCoiYmLZ2WsCIQCPAulEJ4ixUmyLbZsjmaRgWHeeo-MiY8XqK9BVbHJ3qA%3D%3D"

        val frameWidth = resources.getDimensionPixelSize(R.dimen.frame_width) / 4
        val frameHeight = resources.getDimensionPixelSize(R.dimen.frame_height) / 4

        val coefficient = when {
            mDuration < 50000 -> 1
            else -> mDuration / 50
        }

        val normalized = timeUs - timeUs % coefficient

        if (normalized == mLastNormalized) return

        mLastNormalized = normalized

        Log.d("setVideoFrameFor", "setVideoFrameFor $timeUs $normalized $coefficient")

        loadBitmap(url, normalized)
        //mGetFrame(normalized, frameWidth, frameHeight)
    }

    fun update(progressMs: Long, durationMs: Long) {
        if (durationMs < 0) return
        mDuration = durationMs
        if (!mCanUpdate) return
        mLastTotalValue = durationMs
        mBinding.progress.text = TimeUtils.msToTime(progressMs)
        mBinding.total.text = TimeUtils.msToTime(durationMs)
        mBinding.seekBar.progress = (progressMs * 1000 / durationMs).toInt()
    }

    fun setFrameProvider(getFrame: (timeUs: Long, width: Int, height: Int) -> Unit) {
        mGetFrame = getFrame
    }

    fun setVideoFrame(bitmap: Bitmap) {
        mBinding.image.setImageBitmap(bitmap)
    }

}