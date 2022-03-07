package com.alexey.minay.videoplayer

data class VideoPlayerState(
    val url: String,
    val isPlaying: Boolean
) {
    companion object {
        fun default() = VideoPlayerState(
            url = "https://aflet.ispringlearn.ru/proxy/learn-cnode-0/content/200123-9FccD-RoZXp-ZXMAN/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdSI6ImlzbHJ1LTIwMDEyMyIsInV1IjoiNDMzMGM0MTItNjZjNi0xMWViLWI3NTctMGUzM2E1ZmQ0NDBlIiwiY2siOiIyMDAxMjMtOUZjY0QtUm9aWHAtWlhNQU4iLCJhbSI6MiwiZXQiOjE2NDcyNzM4MzZ9.J2goU8A4EKqa3Z4s0rWuC0Wx-tbdiFBYEWScmidCaXk/video.mp4",
            isPlaying = true
        )
    }
}