package com.images.api.data.local.dao

import androidx.room.*
import com.images.api.data.local.model.PixabayImagesEntity

/**
 * Created by ZEESHAN on 5/12/2023.
 */
@Dao
interface ImagesDao {

    @Upsert
    suspend fun saveImage(images: PixabayImagesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveImages(images: List<PixabayImagesEntity>)

    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM ImagesEntity WHERE tags = :query) THEN 1 ELSE 0 END")
    suspend fun isExists(query: String): Boolean

    @Query("SELECT * FROM ImagesEntity  WHERE tags LIKE '%' || :query || '%'")
    suspend fun getImages(query: String): List<PixabayImagesEntity>?

}