package com.spotify.demo.ui.list

import com.spotify.demo.data.models.MovieItem

sealed class ListActivityState
object Init : ListActivityState()
data class Loading(val isMore:Boolean) : ListActivityState()

data class SuccessLoading(val movies: List<MovieItem>) : ListActivityState()
data class SuccessLoadingMore(val movies: List<MovieItem>) : ListActivityState()
data class ErrorLoading(val message: String) : ListActivityState()