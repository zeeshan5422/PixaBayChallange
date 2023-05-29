package com.images.api.util

import com.images.api.data.local.model.PixabayImagesEntity
import com.images.api.data.remote.model.PixabayImagesResponse
import com.images.api.domain.mapper.ImageEntityToModelMapper
import com.images.api.domain.mapper.ImageResponseToEntityMapper
import com.images.api.domain.model.ImageModel

/**
 * Created by ZEESHAN on 5/15/2023.
 */
object ImageDataHolder {


    val imagesEntities = mutableListOf(
        getTempEntity(1, "zeeshan1"),
        getTempEntity(2, "zeeshan2"),
    )

    val imagesResponse = mutableListOf(
        getImageResponse(1, "zeeshan1"),
        getImageResponse(2, "zeeshan2"),
    )

    fun mapImagesResponseToEntity(): List<PixabayImagesEntity> {
        return ImageResponseToEntityMapper().map(imagesResponse)
    }


    fun mapImagesEntityToModel(): List<ImageModel> {
        return ImageEntityToModelMapper().map(imagesEntities)
    }

    private fun getTempEntity(id: Int, user: String) = PixabayImagesEntity(
        id = id,
        cacheTimeInMillis = 1621042925000,
        webformatHeight = 480,
        imageWidth = 800,
        previewHeight = 150,
        webformatURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
        userImageURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
        previewURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
        comments = 10,
        type = "photo",
        imageHeight = 600,
        tags = "food, fruit",
        previewWidth = 200,
        fullHDURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
        downloads = 100,
        userId = 123,
        largeImageURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
        imageURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
        pageURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
        imageSize = 1024,
        webformatWidth = 640,
        user = user,
        views = 500,
        likes = 50
    )

    private fun getImageResponse(id: Int, user: String) = PixabayImagesResponse(
        id = id,
        webformatHeight = 480,
        imageWidth = 800,
        previewHeight = 150,
        webformatURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
        userImageURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
        previewURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
        comments = 10,
        type = "photo",
        imageHeight = 600,
        tags = "food, fruit",
        previewWidth = 200,
        fullHDURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
        downloads = 100,
        userId = 123,
        largeImageURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
        imageURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
        pageURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
        imageSize = 1024,
        webformatWidth = 640,
        user = user,
        views = 500,
        likes = 50
    )

}