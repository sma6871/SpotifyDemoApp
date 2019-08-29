package com.spotify.demo.ui.list

import com.spotify.demo.data.models.ArtistItem

sealed class ListActivityState
object Init : ListActivityState()
object Loading : ListActivityState()
data class SuccessLoading(val artists: List<ArtistItem>) : ListActivityState()
data class ErrorLoading(val message: String) : ListActivityState()