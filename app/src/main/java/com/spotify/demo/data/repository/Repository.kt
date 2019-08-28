package com.spotify.demo.data.repository

import com.spotify.demo.data.models.MovieDiscoverResponse
import io.reactivex.Single

class Repository(
    private val remoteRepo: RemoteRepository,
    private val localRepository: LocalRepository
) {
    fun discoverMovies(year: Int, page: Int = 1): Single<MovieDiscoverResponse> {
        return remoteRepo.discoverMovies(year,page)
    }

}