package com.fourthwall.android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourthwall.android.data.dao.ImageInfoDao
import com.fourthwall.android.data.entity.ImageInfo
import com.fourthwall.android.network.service.PicsumService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Santiago Carrillo
 * 6/11/21.
 */
@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val picsumService: PicsumService,
    private val imageInfoDao: ImageInfoDao,
    private val defaultDispatcher: CoroutineDispatcher

) : ViewModel() {

    val imagesListLiveData: LiveData<List<ImageInfo>> = imageInfoDao.all()

    val loadImagesLiveData: MutableLiveData<Boolean> = MutableLiveData()

    var selectedImageInfo: ImageInfo? = null

    init {
        loadImages()
    }

    fun loadImages() {
        viewModelScope.launch(defaultDispatcher) {
            try {
                val response = picsumService.getImagesList(null)
                if (response.isSuccessful) {
                    val imagesList = response.body()!!
                    for (imageDto in imagesList) {
                        val foundImage = imageInfoDao.findById(imageDto.id)
                        if (foundImage != null) {
                            foundImage.update(imageDto)
                            imageInfoDao.update(foundImage)
                        } else {
                            imageInfoDao.insert(ImageInfo(imageDto))
                        }
                    }
                    loadImagesLiveData.postValue(true)
                } else {
                    loadImagesLiveData.postValue(false)
                }
            } catch (e: Exception) {
                loadImagesLiveData.postValue(false)
            }
        }
    }

}