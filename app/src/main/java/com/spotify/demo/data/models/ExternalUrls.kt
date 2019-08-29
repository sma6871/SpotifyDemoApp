package com.spotify.demo.data.models


import com.google.gson.annotations.SerializedName


data class ExternalUrls(

	@field:SerializedName("spotify")
	val spotify: String? = null
)