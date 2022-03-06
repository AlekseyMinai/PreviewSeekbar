package com.alexey.minay.videoplayer

data class VideoPlayerState(
    val url: String,
    val isPlaying: Boolean
) {
    companion object {
        fun default() = VideoPlayerState(
            url = "https://rr3---sn-5hne6nzk.googlevideo.com/videoplayback?expire=1646602082&ei=AtMkYo-TDJKKhgaXpaWgCA&ip=216.131.105.58&id=o-AJdwu4fSEAWuQBW5kDJCe-wNrA1OOKM3kY69iqNPoO7w&itag=18&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=yusgDZos4MU8DoHr0gWreKoG&gir=yes&clen=101138008&ratebypass=yes&dur=2023.967&lmt=1646502069507684&fexp=24001373,24007246,24162927&c=WEB&txp=5430434&n=cA7Ipo91BiCLFA&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRAIgB3WWGh8-RNaVYsrhXmV9OzWf2buZtyWxxUB2Fqu8uoACID8wl2oXYSkXbExE3-lz0dWv5UJwDt5939DpCjQIrEg6&redirect_counter=1&cm2rm=sn-p5qe7e7s&req_id=f7e6e6e4f50ba3ee&cms_redirect=yes&cmsv=e&mh=gP&mip=136.169.211.3&mm=34&mn=sn-5hne6nzk&ms=ltu&mt=1646579480&mv=u&mvi=3&pl=21&lsparams=mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRQIgD2WmMlkEZpi_Jr61hnJ9XL0Ndx8_J7tSqpQ2vtMmFt4CIQDavPunvy86Y5cZuQBxDrSTwr8DXjZlIqBdN_Q-wgN0Yw%3D%3D",
            isPlaying = true
        )
    }
}