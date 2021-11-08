package com.fourthwall.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fourthwall.android.data.dao.ImageInfoDao
import com.fourthwall.android.data.entity.ImageInfo
import com.fourthwall.android.network.dto.ImageDto
import com.fourthwall.android.network.service.PicsumService
import com.fourthwall.android.ui.viewmodel.ImagesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.util.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ImagesViewModelUnitTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var picsumService: PicsumService

    @Mock
    lateinit var imageInfoDao: ImageInfoDao


    lateinit var imagesViewModel: ImagesViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        imagesViewModel = ImagesViewModel(picsumService, imageInfoDao, mainCoroutineRule.dispatcher)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `load images failed notifies livedata observer with false`() =
        mainCoroutineRule.runBlockingTest {
            val responseBody = mock(ResponseBody::class.java)
            val response: Response<List<ImageDto>> = Response.error(404, responseBody)
            `when`(picsumService.getImagesList(null)).thenReturn(response)

            val observer = Observer<Boolean> {}
            try {

                imagesViewModel.loadImagesLiveData.observeForever(observer)
                imagesViewModel.loadImages()

                val value = imagesViewModel.loadImagesLiveData.value
                Assert.assertEquals(false, value)

            } finally {
                imagesViewModel.loadImagesLiveData.removeObserver(observer)
            }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `load images successful notifies livedata observer with true`() =
        mainCoroutineRule.runBlockingTest {
            val responseList: List<ImageDto> = ArrayList()
            (responseList as ArrayList).add(ImageDto("0", "author 1", "w", "h", "", ""))
            val response: Response<List<ImageDto>> = Response.success(responseList)
            `when`(picsumService.getImagesList(null)).thenReturn(response)

            val observer = Observer<Boolean> {}
            try {

                imagesViewModel.loadImagesLiveData.observeForever(observer)
                imagesViewModel.loadImages()

                val value = imagesViewModel.loadImagesLiveData.value
                Assert.assertEquals(true, value)

            } finally {
                imagesViewModel.loadImagesLiveData.removeObserver(observer)
            }
        }


    @ExperimentalCoroutinesApi
    @Test
    fun `downloaded existing images updates information on dao`() =
        mainCoroutineRule.runBlockingTest {
            val responseList: List<ImageDto> = ArrayList()
            val imageInfoId = "10"
            (responseList as ArrayList).add(ImageDto(imageInfoId, "author 1", "w", "h", "", ""))
            val response: Response<List<ImageDto>> = Response.success(responseList)
            `when`(picsumService.getImagesList(null)).thenReturn(response)
            val foundImageInfo = mock(ImageInfo::class.java)
            `when`(imageInfoDao.findById(imageInfoId)).thenReturn(foundImageInfo)
            val observer = Observer<Boolean> {}
            try {

                imagesViewModel.loadImagesLiveData.observeForever(observer)
                imagesViewModel.loadImages()

                verify(imageInfoDao).update(foundImageInfo)

            } finally {
                imagesViewModel.loadImagesLiveData.removeObserver(observer)
            }
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `downloaded news images saved on dao`() =
        mainCoroutineRule.runBlockingTest {
            val responseList: List<ImageDto> = ArrayList()
            val imageInfoId = "10"
            (responseList as ArrayList).add(ImageDto(imageInfoId, "author 1", "w", "h", "", ""))
            val response: Response<List<ImageDto>> = Response.success(responseList)
            `when`(picsumService.getImagesList(null)).thenReturn(response)
            `when`(imageInfoDao.findById(imageInfoId)).thenReturn(null)
            val observer = Observer<Boolean> {}
            try {

                imagesViewModel.loadImagesLiveData.observeForever(observer)
                imagesViewModel.loadImages()

                verify(imageInfoDao).insert(MockitoHelper.anyObject())

            } finally {
                imagesViewModel.loadImagesLiveData.removeObserver(observer)
            }
        }

}