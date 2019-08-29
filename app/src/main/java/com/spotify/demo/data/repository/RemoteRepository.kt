package com.spotify.demo.data.repository

import com.spotify.demo.data.models.ArtistSearchResponse
import com.spotify.demo.data.remote.AppService
import com.spotify.demo.extensions.observeUiSubscribeIo
import io.reactivex.Single

class RemoteRepository(private val appService: AppService) {

    fun searchArtists(query: String): Single<ArtistSearchResponse> {
        return appService.searchArtist(query).observeUiSubscribeIo()
    }
}