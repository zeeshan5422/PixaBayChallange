package com.images.api.di

import android.content.Context
import com.images.api.BuildConfig
import com.images.api.data.local.AppDatabase
import com.images.api.data.remote.ApiInterface
import com.images.api.data.source.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

/**
 * Created by ZEESHAN on 5/12/2023.
 */

const val DEMO_SOURCE = "DEMO_SOURCE"
const val REMOTE_SOURCE = "REMOTE_SOURCE"

//[^1]
//const val DATA_SOURCE = DEMO_SOURCE
const val DATA_SOURCE = REMOTE_SOURCE

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getAppDatabase(context)
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)

    @Provides
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://pixabay.com") //TODO:: fixme secure this url, e.g. get from firebase remote configs
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

}


@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    @Named(REMOTE_SOURCE)
    abstract fun bindImageRemoteDataSource(source: RemoteImagesDataSourceImpl): RemoteImagesDataSource

    @Binds
    @Named(DEMO_SOURCE)
    abstract fun bindImageDemoDataSource(source: DemoImagesDataSourceImpl): RemoteImagesDataSource

    @Binds
    abstract fun bindImageLocalDataSource(source: LocalImagesDataSourceImpl): LocalImagesDataSource

    companion object {

        @Provides
        fun provideImageLocalDataSource(db: AppDatabase): LocalImagesDataSourceImpl =
            LocalImagesDataSourceImpl(db = db)

        @Provides
        fun provideImageDataSource(api: ApiInterface): RemoteImagesDataSourceImpl =
            RemoteImagesDataSourceImpl(api = api)

        @Provides
        fun provideDemoImageDataSource(@ApplicationContext context: Context): DemoImagesDataSourceImpl =
            DemoImagesDataSourceImpl(context = context)

    }

}