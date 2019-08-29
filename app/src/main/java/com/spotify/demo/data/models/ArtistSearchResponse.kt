package com.spotify.demo.data.models


import com.google.gson.annotations.SerializedName


data class ArtistSearchResponse(

	@field:SerializedName("artists")
	val artists: Artists? = null
)