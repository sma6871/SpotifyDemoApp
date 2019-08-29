package com.spotify.demo.data.repository

import com.spotify.demo.data.models.ArtistSearchResponse
import com.spotify.demo.data.remote.AuthenticatorInterceptor
import io.reactivex.Single

class Repository(
    private val remoteRepo: RemoteRepository,
    private val localRepository: LocalRepository,
    private val authenticatorInterceptor: AuthenticatorInterceptor
) {


    fun updateToken(accessToken: String) {
        authenticatorInterceptor.accessToken = accessToken
    }

    fun searchArtists(query: String): Single<ArtistSearchResponse> {
        return remoteRepo.searchArtists(query)
    }

}