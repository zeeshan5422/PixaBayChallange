package com.images.api.data.source

import com.images.api.data.local.model.PixabayImagesEntity
import com.images.api.data.remote.ApiRepo
import com.images.api.data.remote.model.BaseResponse
import com.images.api.data.remote.model.PixabayImagesResponse
import com.images.api.util.Resource

/**
 * Created by ZEESHAN on 5/12/2023.
 */
interface ImagesDataSource

interface RemoteImagesDataSource : ImagesDataSource, ApiRepo {

    suspend fun fetchImages(query: String): Resource<BaseResponse<PixabayImagesResponse>>

}

interface LocalImagesDataSource : ImagesDataSource {

    suspend fun insertOrUpdate(image: PixabayImagesEntity)

    suspend fun fetchImages(query: String): List<PixabayImagesEntity>?

    suspend fun saveImagesDetails(imagesList: List<PixabayImagesEntity>)

    suspend fun isExists(query : String): Boolean
}
