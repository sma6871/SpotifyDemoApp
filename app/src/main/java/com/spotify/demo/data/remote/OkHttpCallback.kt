package com.spotify.demo.data.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.spotify.demo.extensions.fromJson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


class OkHttpCallback<T>(
    private val onSuccess: (response: T) -> Unit,
    private val onError: (throwable: Throwable) -> Unit,
    private val clazz: Class<T>
) : Callback {
    override fun onFailure(call: Call, e: IOException) {
        onError.invoke(e.cause ?: Throwable())
    }

    override fun onResponse(call: Call, response: Response) {
        try {
            if (!response.isSuccessful) {
                onError.invoke(Throwable(response.message()))
                return
            }
            val result = Gson().fromJson(response.body()?.string() ?: "",clazz)
            onSuccess.invoke(result)
        } catch (throwable: Throwable) {
            onError.invoke(throwable)
        }
    }
}