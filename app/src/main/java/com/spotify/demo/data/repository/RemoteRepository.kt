package com.spotify.demo.data.repository

import com.spotify.demo.data.models.MovieDiscoverResponse
import io.reactivex.Single

class RemoteRepository(private val appService: AppService) {
    fun discoverMovies(year: Int, page: Int): Single<MovieDiscoverResponse> {
        return appService.discoverMovies(year, page).observeUiSubscribeIo()
    }

    fun searchArtists(query: String) {
        return appService.searchArtists(query).observeUiSubscribeIo()
    }
}