package com.spotify.demo.data.repository

import com.spotify.demo.data.models.MovieDiscoverResponse
import com.spotify.demo.data.remote.AppService
import com.spotify.demo.extensions.observeUiSubscribeIo
import io.reactivex.Single

class RemoteRepository(private val appService: AppService) {
    fun discoverMovies(year: Int, page: Int): Single<MovieDiscoverResponse> {
        return appService.discoverMovies(year, page).observeUiSubscribeIo()
    }
}