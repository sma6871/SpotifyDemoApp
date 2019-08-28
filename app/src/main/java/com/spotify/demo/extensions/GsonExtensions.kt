package com.spotify.demo.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

fun Any.toJson():String {
    return Gson().toJson(this)
}