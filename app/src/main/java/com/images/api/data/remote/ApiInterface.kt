package com.images.api.data.remote

import com.images.api.API_KEY
import com.images.api.data.remote.model.BaseResponse
import com.images.api.data.remote.model.PixabayImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ZEESHAN on 5/12/2023.
 */
interface ApiInterface {

    @GET("/api/")
    suspend fun getImages(
        @Query("key") key: String = API_KEY,
        @Query("q") query: String,
        @Query("image_type") imageType: String = "photo"
    ): Response<BaseResponse<PixabayImagesResponse>>
}