package com.spotify.demo.data.repository

import com.spotify.demo.data.models.MovieDiscoverResponse
import com.spotify.demo.data.remote.AuthenticatorInterceptor
import io.reactivex.Single

class Repository(
    private val remoteRepo: RemoteRepository,
    private val localRepository: LocalRepository,
    private val authenticatorInterceptor: AuthenticatorInterceptor
) {
    fun discoverMovies(year: Int, page: Int = 1): Single<MovieDiscoverResponse> {
        return remoteRepo.discoverMovies(year, page)
    }

    fun updateToken(accessToken: String) {
        authenticatorInterceptor.accessToken = accessToken
    }

    fun searchArtists(query: String) {
        return remoteRepo.searchArtists(query)
    }

}