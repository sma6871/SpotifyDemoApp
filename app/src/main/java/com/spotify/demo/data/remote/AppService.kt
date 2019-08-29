package com.spotify.demo.data.remote

import okhttp3.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class AppService(
    val okHttpClient: OkHttpClient,
    val gsonConverterFactory: GsonConverterFactory,
    val baseUrl: String
) {
    fun a(x: Any, y: Any): Unit {
        okHttpClient.newCall(Request.Builder().url(baseUrl).build()).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call, response: Response) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}