package com.alexey.minay.videoplayer

data class VideoPlayerState(
    val url: String,
    val isPlaying: Boolean
) {
    companion object {
        fun default() = VideoPlayerState(
            url = "https://rr5---sn-5hne6nz6.googlevideo.com/videoplayback?expire=1646099073&ei=ISYdYt-CCYTXx_APx6SI0Ag&ip=91.90.122.11&id=o-AGVY5_Yh1FTCVp-PepOQa2baNzb8G_W4Lf6tR91NiDHR&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=6FbGONfo9a9zOq6la4vlgWMG&gir=yes&clen=95781657&ratebypass=yes&dur=1693.152&lmt=1645633965252739&fexp=24001373,24007246&c=WEB&txp=5430434&n=pSzEunD-bfNUuA&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRAIgdX6_BZg4i9gtZ5UWTta_AGQoeLWC3H7GeNxthSkcUeMCIFC6f0LN1oX8pSJpub199yKDRLwa-i2wtv3ZYi_EBoAZ&redirect_counter=1&cm2rm=sn-1gie67e&req_id=232ab90b8760a3ee&cms_redirect=yes&cmsv=e&mh=HJ&mip=136.169.211.3&mm=34&mn=sn-5hne6nz6&ms=ltu&mt=1646077183&mv=u&mvi=5&pl=21&lsparams=mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRQIgKGtCeHjTdl6_hXdmDp44qZDqzzYJeZ72CCoiYmLZ2WsCIQCPAulEJ4ixUmyLbZsjmaRgWHeeo-MiY8XqK9BVbHJ3qA%3D%3D",
            //url = "https://firebasestorage.googleapis.com/v0/b/testfirebase-a2d24.appspot.com/o/videos%2F%D0%90%D0%BD%D0%B3%D0%BB%D0%B8%D0%B9%D1%81%D0%BA%D0%B8%D0%B9%20%D1%8F%D0%B7%D1%8B%D0%BA%20%D1%81%20%D0%BD%D1%83%D0%BB%D1%8F%20%D0%B7%D0%B0%2050%20%D1%83%D1%80%D0%BE%D0%BA%D0%BE%D0%B2%20A0%20%20%D0%90%D0%BD%D0%B3%D0%BB%D0%B8%D0%B9%D1%81%D0%BA%D0%B8%D0%B9%20%D1%81%20%D0%BD%D1%83%D0%BB%D1%8F%20%20%D0%90%D0%BD%D0%B3%D0%BB%D0%B8%D0%B9%D1%81%D0%BA%D0%B8%D0%B9%20%D0%B4%D0%BB%D1%8F%20%D0%BD%D0%B0%D1%87%D0%B8%D0%BD%D0%B0%D1%8E%D1%89%D0%B8%D1%85%20%20%D0%A3%D1%80%D0%BE%D0%BA%D0%B8%20%D0%A3%D1%80%D0%BE%D0%BA%202.mp4?alt=media&token=e1d0638b-7e27-4d57-8b99-64e076649110",
            isPlaying = true
        )
    }
}