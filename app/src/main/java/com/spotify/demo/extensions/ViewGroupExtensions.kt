package com.spotify.demo.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

public fun ViewGroup.inflate(
    layoutRes: Int,
    root: ViewGroup = this,
    attachToRoot: Boolean = false
): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

