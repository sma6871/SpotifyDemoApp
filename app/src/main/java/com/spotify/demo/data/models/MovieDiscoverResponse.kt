package com.spotify.demo.data.models

import com.google.gson.annotations.SerializedName

data class MovieDiscoverResponse(

    @field:SerializedName("page")
	val page: Int? = null,

    @field:SerializedName("total_pages")
	val totalPages: Int? = null,

    @field:SerializedName("results")
	val results: List<MovieItem>? = null,

    @field:SerializedName("total_results")
	val totalResults: Int? = null
)