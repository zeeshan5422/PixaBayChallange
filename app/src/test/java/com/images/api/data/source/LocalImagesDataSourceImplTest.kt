package com.images.api.data.source

import com.images.api.data.local.AppDatabase
import com.images.api.data.local.dao.ImagesDao
import com.images.api.util.ImageDataHolder
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Created by ZEESHAN on 5/15/2023.
 */
class LocalImagesDataSourceImplTest {

    private lateinit var localImagesDataSource: LocalImagesDataSourceImpl

    @Mock
    private lateinit var mockDb: AppDatabase

    @Mock
    private lateinit var mockImagesDao: ImagesDao

    private var imagesList = ImageDataHolder.imagesEntities

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        `when`(mockDb.getImagesDao()).thenReturn(mockImagesDao)
        localImagesDataSource = LocalImagesDataSourceImpl(mockDb)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun upsertImage_shouldUpsertImageToDatabase() = runBlocking {

        // Given
        val query = "food"
        val expectedResult = imagesList[0]
        `when`(mockImagesDao.getImages(query)).thenReturn(imagesList)

        // When
        localImagesDataSource.insertOrUpdate(imagesList[0])

        // Then
        val result = localImagesDataSource.fetchImages(query)!![0]
        assertThat(result, equalTo(expectedResult))
    }

    @Test
    fun saveImagesDetails_shouldSaveImagesToDatabase() = runBlocking {
        // Given

        // When
        localImagesDataSource.saveImagesDetails(imagesList)

        // Then
        verify(mockImagesDao, times(1)).saveImages(imagesList)
    }

    @Test
    fun isExists_shouldReturnCorrectResultFromDatabase() = runBlocking {
        // Given
        val query = "fruits"
        val expectedResult = true
        `when`(mockImagesDao.isExists(query)).thenReturn(expectedResult)

        // When
        val result = localImagesDataSource.isExists(query)

        // Then
        verify(mockImagesDao, times(1)).isExists(query)
        assertThat(result, equalTo(expectedResult))
    }

    @Test
    fun fetchImages_shouldReturnImagesFromDatabase() = runBlocking {
        // Given
        val query = "food"
        `when`(mockImagesDao.getImages(query)).thenReturn(imagesList)

        // When
        val result = localImagesDataSource.fetchImages(query)

        // Then
        verify(mockImagesDao, times(1)).getImages(query)
        assertThat(result, equalTo(imagesList))
    }


}