package com.spotify.demo.data.remote

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class OkHttpCallback<T>(
    val onSuccess: (response: T) -> Unit,
    val onError: (throwable: Throwable?) -> Unit
) : Callback {
    override fun onFailure(call: Call, e: IOException) {
        onError.invoke(e.cause)
    }

    override fun onResponse(call: Call, response: Response) {
        try {

            if (!response.isSuccessful) {
                onError.invoke(Throwable(response.message()))
                return
            }
            val result = response.body() as T
            onSuccess.invoke(result)
        } catch (throwable: Throwable) {
            onError.invoke(throwable)
        }
    }
}