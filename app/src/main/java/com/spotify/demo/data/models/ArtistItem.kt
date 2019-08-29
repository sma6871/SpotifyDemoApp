package com.spotify.demo.data.models


import com.google.gson.annotations.SerializedName


data class ArtistItem(

	@field:SerializedName("images")
	val images: List<ImagesItem>? = null,

	@field:SerializedName("followers")
	val followers: Followers? = null,

	@field:SerializedName("genres")
	val genres: List<String>? = null,

	@field:SerializedName("popularity")
	val popularity: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("href")
	val href: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("external_urls")
	val externalUrls: ExternalUrls? = null,

	@field:SerializedName("uri")
	val uri: String? = null
)