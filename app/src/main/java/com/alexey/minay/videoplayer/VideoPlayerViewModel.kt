package com.alexey.minay.videoplayer

import androidx.lifecycle.ViewModel
import com.alexey.minay.videoplayer.utils.modify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class VideoPlayerViewModel : ViewModel() {

    val state get() = mState.asStateFlow()
    private val mState = MutableStateFlow(VideoPlayerState.default())

    fun changePlayingState() {
        mState.modify {
            copy(isPlaying = !isPlaying)
        }
    }

}
