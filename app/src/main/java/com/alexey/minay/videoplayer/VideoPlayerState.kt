package com.alexey.minay.videoplayer

data class VideoPlayerState(
    val url: String,
    val isPlaying: Boolean
) {
    companion object {
        fun default() = VideoPlayerState(
            url = "https://aflet.ispringlearn.ru/proxy/learn-cnode-0/content/200123-BEjaq-6tWuf-z3f63/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdSI6ImlzbHJ1LTIwMDEyMyIsInV1IjoiNDMzMGM0MTItNjZjNi0xMWViLWI3NTctMGUzM2E1ZmQ0NDBlIiwiY2siOiIyMDAxMjMtQkVqYXEtNnRXdWYtejNmNjMiLCJhbSI6MiwiZXQiOjE2NDczNTUxMzB9.UEaD6RgQxYG7J36yK13Bzlb7YWEAU_LW_VwY758O8E0/video.mp4",
            isPlaying = true
        )
    }
}