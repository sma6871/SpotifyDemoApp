package com.spotify.demo.data.remote

import com.spotify.demo.constants.ApplicationConstants
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticatorInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url: HttpUrl = request.url()
            .newBuilder()
            .addQueryParameter(ApplicationConstants.API_KEY, "4e5dbb0f9418ec39f763bc1014af0b15")
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)

    }
}