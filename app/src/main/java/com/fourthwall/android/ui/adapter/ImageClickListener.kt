package com.fourthwall.android.ui.adapter

import com.fourthwall.android.data.entity.ImageInfo

/**
 * @author Santiago Carrillo
 * 6/11/21.
 */
interface ImageClickListener {

    fun onImageClicked(imageInfo: ImageInfo)
}