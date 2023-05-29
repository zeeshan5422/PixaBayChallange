package com.images.api.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by ZEESHAN on 5/12/2023.
 */
@Parcelize
data class ImageModel(

    val id: Int = 0,
    val user: String? = null,
    val tags: List<String> = emptyList(),
    val views: Int? = null,

    val previewURL: String? = null,
    val webformatURL: String? = null,

    val likes: Int? = null,
    val downloads: Int? = null,
    val comments: Int? = null,
    val cacheTimeInMillis: Long = 0
) : Parcelable
