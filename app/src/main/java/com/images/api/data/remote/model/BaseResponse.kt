package com.images.api.data.remote.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(

	@field:SerializedName("hits")
	val hits: List<T>? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("totalHits")
	val totalHits: Int? = null
)
