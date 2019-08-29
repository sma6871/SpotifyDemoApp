package com.spotify.demo.extensions

import com.spotify.demo.data.remote.OkHttpCallback
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.Request


fun <T> OkHttpClient.callRx(request: Request,clazz: Class<T>): Single<T> {
    return Single.create<T> { emitter ->
        this.newCall(request).enqueue(OkHttpCallback({ response ->
            emitter.onSuccess(response)
        }, { throwable ->
            emitter.onError(throwable)
        },clazz))
    }
}
