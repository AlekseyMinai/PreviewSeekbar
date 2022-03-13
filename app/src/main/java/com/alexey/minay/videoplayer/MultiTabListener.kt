package com.alexey.minay.videoplayer

import android.util.Log
import android.util.TypedValue
import android.view.View
import kotlinx.coroutines.*

class MultiTabListener(
    private val coroutineScope: CoroutineScope,
    private val button: View
) : View.OnClickListener {

    private var mClickCount = 0
        set(value) {
            Log.d("MultiTabListener", "MultiTabListener $value")

            when (value) {
                0 -> button.setBackgroundResource(0)
                2 -> button.addCircleRipple()
                else -> Unit
            }
            field = value
        }
    private var mWaitJob: Job? = null

    override fun onClick(v: View?) {
        waitNextClick {  }
    }

    private fun waitNextClick(submit: (Int) -> Unit) {
        mClickCount++
        mWaitJob?.cancel()
        mWaitJob = coroutineScope.launch(Dispatchers.Main) {
            delay(DURATION_BETWEEN_CLICK)
            submit(mClickCount)
            mClickCount = 0
        }
    }

    private fun View.addCircleRipple() = with(TypedValue()) {
        context.theme.resolveAttribute(
            android.R.attr.selectableItemBackgroundBorderless, this, true
        )
        setBackgroundResource(resourceId)
    }

    companion object {
        private const val DURATION_BETWEEN_CLICK = 400L
    }
}