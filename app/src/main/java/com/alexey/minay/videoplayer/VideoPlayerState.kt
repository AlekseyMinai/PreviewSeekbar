package com.alexey.minay.videoplayer

data class VideoPlayerState(
    val url: String,
    val isPlaying: Boolean
) {
    companion object {
        fun default() = VideoPlayerState(
            //url = "https://rr5---sn-4g5edns6.googlevideo.com/videoplayback?expire=1647186266&ei=-rwtYoeHNoyVsfIPmP6QsA4&ip=64.145.94.4&id=o-ABYtY2DrBouA6W1Jf7-Ebo8PS1eveiFTJjLTsx5SI986&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=LOh5O8EWBk3mnkykqrHxfxwG&gir=yes&clen=28518371&ratebypass=yes&dur=638.014&lmt=1590416785765318&fexp=24001373,24007246,24162928&c=WEB&txp=5531432&n=VQaVxFnpw-g_Ig&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIgRwtWnpZu0hkDt9U5Q6y7x9uzNI-5N5m9gTY7xBwfmFoCIQDbs-Jl0jog55rEEWTK76kUdaSkQ9426mPkA8mZ5OVNLA%3D%3D&redirect_counter=1&cm2rm=sn-a5me7z7s&req_id=811a29fd905ca3ee&cms_redirect=yes&cmsv=e&mh=IB&mip=136.169.211.3&mm=34&mn=sn-4g5edns6&ms=ltu&mt=1647167478&mv=u&mvi=5&pl=22&lsparams=mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRAIgENGiztU3krr8OiwSyRRTlCJRbmwuD8BMdPKBlAXePSACIBQ4FhLJ3yqYVUQjDMEK_lfy0nwOsefzfd6-NFem7e6f",
            url = "https://aflet.ispringlearn.ru/proxy/learn-cnode-0/content/200123-BEjaq-6tWuf-z3f63/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdSI6ImlzbHJ1LTIwMDEyMyIsInV1IjoiNDMzMGM0MTItNjZjNi0xMWViLWI3NTctMGUzM2E1ZmQ0NDBlIiwiY2siOiIyMDAxMjMtQkVqYXEtNnRXdWYtejNmNjMiLCJhbSI6MiwiZXQiOjE2NDczNTUxMzB9.UEaD6RgQxYG7J36yK13Bzlb7YWEAU_LW_VwY758O8E0/video.mp4",
            isPlaying = true
        )
    }
}