package com.images.api.domain

import com.images.api.data.local.model.PixabayImagesEntity
import com.images.api.data.source.LocalImagesDataSource
import com.images.api.data.source.RemoteImagesDataSource
import com.images.api.di.DATA_SOURCE
import com.images.api.domain.mapper.ImageEntityToModelMapper
import com.images.api.domain.mapper.ImageResponseToEntityMapper
import com.images.api.domain.model.ImageModel
import com.images.api.util.*
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by ZEESHAN on 5/12/2023.
 */
class ImagesUseCase @Inject constructor(
    private val localSource: LocalImagesDataSource,
    @Named(DATA_SOURCE)
    private val remoteSource: RemoteImagesDataSource,
    private val responseToEntityMapper: ImageResponseToEntityMapper,
    private val entityMapper: ImageEntityToModelMapper
) {
    suspend operator fun invoke(query: String): Flow<Resource<List<ImageModel>>> {

        return getLocalElseFromNetwork(
            query = {
                val queryData: List<PixabayImagesEntity>? = localSource.fetchImages(query)
                if (queryData.isNullOrEmpty()) {
                    Resource.Failure(ResourceError(errorCode = ERROR_EMPTY_DATA))
                } else {
                    Resource.Success(responseData = entityMapper.map(queryData))
                }
            }, fetch = {
                remoteSource.fetchImages(query)
            }, saveFetchResult = {
                val mappedList =
                    responseToEntityMapper.map(it.hits ?: emptyList())
                localSource.saveImagesDetails(mappedList)
            }, shouldFetch = {
                val minValidMillis = Calendar.getInstance().timeInMillis - DateUtils.excludeHours()
                it.data.isNullOrEmpty() || it.data.first().cacheTimeInMillis > minValidMillis
            }
        )

    }
}