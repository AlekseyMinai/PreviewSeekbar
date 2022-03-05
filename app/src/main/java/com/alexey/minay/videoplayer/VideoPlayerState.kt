package com.alexey.minay.videoplayer

data class VideoPlayerState(
    val url: String,
    val isPlaying: Boolean
) {
    companion object {
        fun default() = VideoPlayerState(
            url = "https://rr11---sn-n8v7kn7e.googlevideo.com/videoplayback?expire=1646537803&ei=69cjYtamIbqjx_APmMCs8AY&ip=216.131.114.54&id=o-AInBDwuJSFM-krjGpfZoKKzfQx3HWqu7LIBwCdLo9Mlw&itag=18&source=youtube&requiressl=yes&pcm2=no&vprv=1&mime=video%2Fmp4&ns=U1UvevgM-mzTeplxWQVwmpkG&gir=yes&clen=73887140&ratebypass=yes&dur=1413.375&lmt=1646299172583233&fexp=24001373,24007246,24162928&c=WEB&txp=5530434&n=ciwiIzsDZV8Dcg&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cpcm2%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIgO2QuTbt0szoZXVouqx_1axuwIOPf38ohZUG8wNj2pjQCIQCBWW9VjNhnxIlERYUwUzjpnuLuxJZ2qNqTADoYk5BzXg%3D%3D&redirect_counter=1&rm=sn-4g5ezz7z&req_id=316f8cb09acba3ee&cms_redirect=yes&cmsv=e&ipbypass=yes&mh=mF&mip=136.169.211.3&mm=31&mn=sn-n8v7kn7e&ms=au&mt=1646515987&mv=m&mvi=11&pl=24&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRQIhANMD37EdVCUw6QYUJBwZlP6kLtmnXzxMDsWjb-MFAUSCAiB7vRZUSWvbw2RGv0gLO_WrOdC2cSfP5sfmpncYNZdWTg%3D%3D",
            isPlaying = true
        )
    }
}