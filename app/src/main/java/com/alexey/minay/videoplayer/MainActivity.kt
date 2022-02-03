package com.alexey.minay.videoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexey.minay.videoplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.videoFrameSeekBar.update(100, 1000)
    }

}