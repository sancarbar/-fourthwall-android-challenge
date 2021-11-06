package com.fourthwall.android.network.service

import com.fourthwall.android.network.dto.ImageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Santiago Carrillo
 * 6/11/21.
 */
interface PicsumService {


    @GET("v2/list")
    suspend fun getImagesList(@Query("page") page: Int?): Response<List<ImageDto>>

    @GET("https://picsum.photos/id/{imageId}/info")
    suspend fun getImageInfo(@Path("imageId") imageId: String): Response<ImageDto>

}