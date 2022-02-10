package com.alexey.minay.videoplayer

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Build
import androidx.core.graphics.scale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoFramePool(
    private val url: String
) {

    private val mFrames = mutableMapOf<Long, Bitmap>()

    suspend fun getScaledFrameAtTime(timeUs: Long, width: Int, height: Int): Bitmap? =
        withContext(Dispatchers.IO) {
            val bitmap = mFrames[timeUs]
            if (bitmap != null && !bitmap.isRecycled) {
                return@withContext bitmap
            }

//            if (mFrames.size > 15) {
//                mFrames[]
//            }

            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(url, emptyMap())

            return@withContext if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                retriever.getScaledFrameAtTime(
                    timeUs,
                    MediaMetadataRetriever.OPTION_CLOSEST,
                    width,
                    height
                )
            } else {
                retriever.getFrameAtTime(timeUs)?.scale(width, height)
            }.also { bitmap ->
                if (bitmap != null) {
                    mFrames[timeUs] = bitmap
                }
            }
        }

    fun recycle() {
        mFrames.forEach { it.value.recycle() }
        mFrames.clear()
    }

}