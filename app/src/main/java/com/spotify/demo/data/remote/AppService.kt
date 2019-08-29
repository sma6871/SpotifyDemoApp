package com.spotify.demo.data.remote

import com.spotify.demo.constants.QueryParameterKeys
import com.spotify.demo.constants.QueryType
import com.spotify.demo.data.models.ArtistSearchResponse
import com.spotify.demo.extensions.callRx
import io.reactivex.Single
import okhttp3.*
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.HttpUrl


class AppService(
    val okHttpClient: OkHttpClient,
    val gsonConverterFactory: GsonConverterFactory,
    val baseUrl: String
) {
    fun searchArtist(query: String): Single<ArtistSearchResponse> {
        val urlBuilder = HttpUrl.parse("$baseUrl/search")!!.newBuilder()
        urlBuilder.addQueryParameter(QueryParameterKeys.Q, query)
        urlBuilder.addQueryParameter(QueryParameterKeys.Type, QueryType.Artist.value)
        val url = urlBuilder.build().toString()
        val request = Request.Builder()
            .url(url)
            .build()

        return okHttpClient.callRx(request, ArtistSearchResponse::class.java)
    }
}