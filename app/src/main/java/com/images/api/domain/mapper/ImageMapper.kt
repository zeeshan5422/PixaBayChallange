package com.images.api.domain.mapper

import com.images.api.data.local.model.PixabayImagesEntity
import com.images.api.data.remote.model.PixabayImagesResponse
import com.images.api.domain.model.ImageModel
import java.util.*
import javax.inject.Inject

/**
 * Created by ZEESHAN on 5/12/2023.
 */

interface Mapper<INPUT, OUTPUT> {
    fun map(input: INPUT): OUTPUT
}

interface ListMapper<INPUT, OUTPUT> {
    fun map(input: List<INPUT>): List<OUTPUT>
}

open class ImageResponseToEntityMapper @Inject constructor() : ListMapper<PixabayImagesResponse,PixabayImagesEntity> {


    override fun map(input: List<PixabayImagesResponse>): List<PixabayImagesEntity> {
        val output = mutableListOf<PixabayImagesEntity>()
        val cacheTimeInMillis = Calendar.getInstance().timeInMillis

        input.forEach {
            val result = PixabayImagesEntity(
                id = it.id,
                webformatHeight = it.webformatHeight,
                cacheTimeInMillis = cacheTimeInMillis,
                imageWidth = it.imageWidth,
                previewHeight = it.previewHeight,
                webformatURL = it.webformatURL,
                userImageURL = it.userImageURL,
                previewURL = it.previewURL,
                comments = it.comments,
                type = it.type,
                imageHeight = it.imageHeight,
                tags = it.tags,
                previewWidth = it.previewWidth,
                fullHDURL = it.fullHDURL,
                downloads = it.downloads,
                userId = it.userId,
                largeImageURL = it.largeImageURL,
                imageURL = it.imageURL,
                pageURL = it.pageURL,
                imageSize = it.imageSize,
                webformatWidth = it.webformatWidth,
                user = it.user,
                views = it.views,
                likes = it.likes
            )
            output.add(result)
        }
        return output
    }
}

open class ImageEntityToModelMapper @Inject constructor() : ListMapper<PixabayImagesEntity,ImageModel>{

    override fun map(input: List<PixabayImagesEntity>): List<ImageModel> {

        val output = mutableListOf<ImageModel>()

        input.forEach {
            val result = ImageModel(
                id = it.id,
                webformatURL = it.webformatURL,
                previewURL = it.previewURL,
                comments = it.comments,
                tags = splitTags(it.tags),
                downloads = it.downloads,
                user = it.user,
                views = it.views,
                likes = it.likes,
                cacheTimeInMillis = it.cacheTimeInMillis
            )
            output.add(result)
        }
        return output

    }

    private fun splitTags(tags: String?): List<String> {
        return if (tags.isNullOrBlank()) {
            return emptyList()
        } else if (!tags.contains(",")) {
            return listOf(tags)
        } else {
            tags.split(",")
        }
    }

}