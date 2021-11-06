package com.fourthwall.android.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourthwall.android.data.dao.ImageInfoDao
import com.fourthwall.android.data.entity.ImageInfo
import com.fourthwall.android.network.service.PicsumService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
) : ViewModel() {

    val imagesListLiveData: LiveData<List<ImageInfo>> = imageInfoDao.all()

    init {
        loadImages()
    }

    private fun loadImages() {
        viewModelScope.launch(Dispatchers.IO) {
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
                }
            } catch (e: Exception) {
                Log.e("Developer", "Error", e)
            }
        }
    }

}