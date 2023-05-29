package com.images.api.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.images.api.domain.ImagesUseCase
import com.images.api.domain.model.ImageModel
import com.images.api.util.ResourceError
import com.images.api.util.UiState
import com.images.api.util.emptyException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val imagesUseCase: ImagesUseCase) :
    ViewModel() {

    val query : MutableLiveData<String> = MutableLiveData("fruits")
    lateinit var selectedImage: ImageModel

    private val _data = MutableLiveData<UiState<List<ImageModel>>>(UiState.Initial)
    val data: LiveData<UiState<List<ImageModel>>> = _data

    init {
        fetchImagesData()
    }


    fun fetchImagesData() = viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) {
            _data.value = UiState.Loading
        }
        imagesUseCase.invoke(query.value!!).collect {
            withContext(Dispatchers.Main) {
                if (it.isSuccessful) {
                    _data.value = UiState.Success(it.data ?: emptyList())
                } else {
                    _data.value = UiState.Fail(it.apiError ?: emptyException())
                }
            }
        }
    }

    fun setImage(imageModel: ImageModel) {
        selectedImage = imageModel
    }

    fun getImage(): ImageModel {
        return selectedImage
    }

    fun setQuery(text: String) {
        query.value = text
    }

}