package com.alexey.minay.videoplayer

data class VideoPlayerState(
    val url: String,
    val isPlaying: Boolean
) {
    companion object {
        fun default() = VideoPlayerState(
            url = "https://rr11---sn-n8v7kn7l.googlevideo.com/videoplayback?expire=1646188968&ei=SIUeYoThIeOrxN8P7OaXkAM&ip=185.108.106.210&id=o-AP3PWwkWe2pU2BfYY2Xfyh-Z2mKBgx-6i7Lxpq_BpO56&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=RJDG4tN8vNxTMSg7vcUiEC8G&gir=yes&clen=148693097&ratebypass=yes&dur=2012.437&lmt=1645850678380880&fexp=24001373,24007246,24162927&c=WEB&txp=4430434&n=icKd1GdSKd0GYA&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIgQhoGR32bcRDCD974VOrXlPz2831ZPtVt5YHq-bzhM8ECIQCCQuNxbK-o7ULIzX2LQsX5jRlXWkUQqlTd5AhXM5G73A%3D%3D&redirect_counter=1&rm=sn-5hness7l&req_id=c3885de10576a3ee&cms_redirect=yes&cmsv=e&ipbypass=yes&mh=s-&mip=136.169.211.3&mm=31&mn=sn-n8v7kn7l&ms=au&mt=1646167259&mv=m&mvi=11&pl=24&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRgIhAI82cxiH-ySZPoh4WO9d5FVzI-sRJz6aYM2s3agFhFMWAiEAo3K8JhDq1wNzEmlmTTQemH-r0nIuufA-twY3MrjzhL4%3D",
//url = "https://firebasestorage.googleapis.com/v0/b/testfirebase-a2d24.appspot.com/o/videos%2F%D0%90%D0%BD%D0%B3%D0%BB%D0%B8%D0%B9%D1%81%D0%BA%D0%B8%D0%B9%20%D1%8F%D0%B7%D1%8B%D0%BA%20%D1%81%20%D0%BD%D1%83%D0%BB%D1%8F%20%D0%B7%D0%B0%2050%20%D1%83%D1%80%D0%BE%D0%BA%D0%BE%D0%B2%20A0%20%20%D0%90%D0%BD%D0%B3%D0%BB%D0%B8%D0%B9%D1%81%D0%BA%D0%B8%D0%B9%20%D1%81%20%D0%BD%D1%83%D0%BB%D1%8F%20%20%D0%90%D0%BD%D0%B3%D0%BB%D0%B8%D0%B9%D1%81%D0%BA%D0%B8%D0%B9%20%D0%B4%D0%BB%D1%8F%20%D0%BD%D0%B0%D1%87%D0%B8%D0%BD%D0%B0%D1%8E%D1%89%D0%B8%D1%85%20%20%D0%A3%D1%80%D0%BE%D0%BA%D0%B8%20%D0%A3%D1%80%D0%BE%D0%BA%202.mp4?alt=media&token=e1d0638b-7e27-4d57-8b99-64e076649110",
            isPlaying = true
        )
    }
}