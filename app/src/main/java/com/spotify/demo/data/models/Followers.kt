package com.spotify.demo.data.models


import com.google.gson.annotations.SerializedName


data class Followers(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("href")
	val href: Any? = null
)