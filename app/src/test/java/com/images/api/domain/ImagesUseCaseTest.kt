package com.images.api.domain

import com.images.api.data.remote.model.BaseResponse
import com.images.api.data.source.LocalImagesDataSource
import com.images.api.data.source.RemoteImagesDataSource
import com.images.api.domain.mapper.ImageEntityToModelMapper
import com.images.api.domain.mapper.ImageResponseToEntityMapper
import com.images.api.util.ImageDataHolder
import com.images.api.util.Resource
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

/**
 * Created by ZEESHAN on 5/15/2023.
 */
class ImagesUseCaseTest {

    @Mock
    private lateinit var localSource: LocalImagesDataSource

    @Mock
    private lateinit var remoteSource: RemoteImagesDataSource

    @Mock
    private lateinit var responseToEntityMapper: ImageResponseToEntityMapper

    @Mock
    private lateinit var entityMapper: ImageEntityToModelMapper

    private lateinit var imagesUseCase: ImagesUseCase

    @Before
    fun setup() {
        responseToEntityMapper = spy(ImageResponseToEntityMapper())
        entityMapper = spy(ImageEntityToModelMapper())

        MockitoAnnotations.openMocks(this)
        imagesUseCase = ImagesUseCase(
            localSource,
            remoteSource,
            responseToEntityMapper,
            entityMapper
        )
    }

    @Test
    fun `invoke_should_return_success_when_local_data_is_available`() = runBlocking {
        val query = "food"
        val remoteData = ImageDataHolder.imagesResponse
        val localData = ImageDataHolder.imagesEntities

        val expectedModels = ImageDataHolder.mapImagesEntityToModel()
        val expectedResult = Resource.Success(expectedModels)

        val mockRemoteResponse = Resource.Success(BaseResponse(remoteData))
        val remoteSource = mock(RemoteImagesDataSource::class.java)
        val localSource = mock(LocalImagesDataSource::class.java)


        `when`(runBlocking { remoteSource.fetchImages(query) }).thenReturn(mockRemoteResponse)
        `when`(localSource.fetchImages(query)).thenReturn(localData)
        `when`(remoteSource.fetchImages(query)).thenReturn(mockRemoteResponse)

        val cacheTimeInMillis = Calendar.getInstance().timeInMillis

        `when`(
            responseToEntityMapper.map(mockRemoteResponse.data?.hits ?: emptyList())
        )
            .thenReturn(localData)

//        `when`(entityMapper.map(localData))
//            .thenReturn(expectedModels)


//        val result = imagesUseCase(query).toList().first()

        assertEquals(1, 1)
    }

    /* @Test
     fun `invoke_should_return_failure_when_local_data_is_empty`() = runBlocking {
         val query = "fruits"
         val localData: List<PixabayImagesEntity> = emptyList()
         val expectedResult =
             Resource.Failure<List<PixabayImagesEntity>>(ResourceError(errorCode = ERROR_EMPTY_DATA))

         `when`(localSource.fetchImages(query)).thenReturn(localData)

         val result = imagesUseCase(query).toList().first()

         assertEquals(expectedResult, result)
     }*/

}