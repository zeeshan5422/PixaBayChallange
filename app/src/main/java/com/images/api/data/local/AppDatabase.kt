package com.images.api.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.images.api.data.local.dao.ImagesDao
import com.images.api.data.local.model.PixabayImagesEntity

/**
 * Created by ZEESHAN on 5/12/2023.
 */

@Database(version = 1, entities = [PixabayImagesEntity::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun getImagesDao(): ImagesDao

    companion object {
        fun getAppDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "Pixabay-db").build()
    }
}