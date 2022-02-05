package com.alexey.minay.videoplayer.utils

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<T>.modify(modifier: T.() -> T) {
    value = value.modifier()
}