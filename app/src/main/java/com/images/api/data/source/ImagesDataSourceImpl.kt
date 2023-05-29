package com.images.api.data.source

import android.content.Context
import com.images.api.data.local.AppDatabase
import com.images.api.data.local.model.PixabayImagesEntity
import com.images.api.data.remote.ApiInterface
import com.images.api.data.remote.getTypeToken
import com.images.api.data.remote.model.BaseResponse
import com.images.api.data.remote.model.PixabayImagesResponse
import com.images.api.util.Resource

/**
 * Created by ZEESHAN on 5/12/2023.
 */

class LocalImagesDataSourceImpl constructor(private val db: AppDatabase) :
    LocalImagesDataSource {

    override suspend fun insertOrUpdate(image: PixabayImagesEntity) {
        db.getImagesDao().saveImage(image)
    }

    override suspend fun saveImagesDetails(imagesList: List<PixabayImagesEntity>) {
        db.getImagesDao().saveImages(imagesList)
    }

    override suspend fun isExists(query: String): Boolean {
        return db.getImagesDao().isExists(query)
    }

    override suspend fun fetchImages(query: String): List<PixabayImagesEntity>? {
        return db.getImagesDao().getImages(query)
    }

}


class RemoteImagesDataSourceImpl constructor(private val api: ApiInterface) :
    RemoteImagesDataSource {

    override suspend fun fetchImages(query: String): Resource<BaseResponse<PixabayImagesResponse>> {
        return api.getImages(query = query).handle()
    }

}

class DemoImagesDataSourceImpl constructor(val context: Context) :
    RemoteImagesDataSource {

    override suspend fun fetchImages(query: String): Resource<BaseResponse<PixabayImagesResponse>> {
        return getDataFromPath(
            context,
            "images_list",
            getTypeToken<BaseResponse<PixabayImagesResponse>>()
        )
    }

}
