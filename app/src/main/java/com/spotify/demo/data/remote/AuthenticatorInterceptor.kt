package com.spotify.demo.data.remote

import android.content.SharedPreferences
import com.spotify.demo.constants.ApplicationConstants
import com.spotify.demo.constants.HeaderKeys
import com.spotify.demo.constants.SharedPrefKeys
import com.spotify.demo.extensions.get
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticatorInterceptor(sharedPreferences: SharedPreferences) : Interceptor {

    var accessToken = ""

    init {
        accessToken = sharedPreferences[SharedPrefKeys.AccessToken, ""]
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Accept", "application/json;")
        request.addHeader("Content-Type", "application/json;")
        if (accessToken.isNotEmpty())
            request.addHeader(HeaderKeys.Authorization, "Bearer $accessToken")
        return chain.proceed(request.build())

    }
}