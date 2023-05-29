package com.images.api.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ImagesEntity")
data class PixabayImagesEntity(


    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "cacheTimeInMillis")
    val cacheTimeInMillis: Long = 0,

    @ColumnInfo(name = "webformatHeight")
    val webformatHeight: Int? = null,

    @ColumnInfo(name = "imageWidth")
    val imageWidth: Int? = null,

    @ColumnInfo(name = "previewHeight")
    val previewHeight: Int? = null,

    @ColumnInfo(name = "webformatURL")
    val webformatURL: String? = null,

    @ColumnInfo(name = "userImageURL")
    val userImageURL: String? = null,

    @ColumnInfo(name = "previewURL")
    val previewURL: String? = null,

    @ColumnInfo(name = "comments")
    val comments: Int? = null,

    @ColumnInfo(name = "type")
    val type: String? = null,

    @ColumnInfo(name = "imageHeight")
    val imageHeight: Int? = null,

    @ColumnInfo(name = "tags")
    val tags: String? = null,

    @ColumnInfo(name = "previewWidth")
    val previewWidth: Int? = null,

    @ColumnInfo(name = "fullHDURL")
    val fullHDURL: String? = null,

    @ColumnInfo(name = "downloads")
    val downloads: Int? = null,

    @ColumnInfo(name = "user_id")
    val userId: Int? = null,

    @ColumnInfo(name = "largeImageURL")
    val largeImageURL: String? = null,

    @ColumnInfo(name = "imageURL")
    val imageURL: String? = null,

    @ColumnInfo(name = "pageURL")
    val pageURL: String? = null,

    @ColumnInfo(name = "imageSize")
    val imageSize: Int? = null,

    @ColumnInfo(name = "webformatWidth")
    val webformatWidth: Int? = null,

    @ColumnInfo(name = "user")
    val user: String? = null,

    @ColumnInfo(name = "views")
    val views: Int? = null,

    @ColumnInfo(name = "likes")
    val likes: Int? = null
)