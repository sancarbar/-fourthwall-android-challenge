package com.fourthwall.android.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

/**
 * @author Santiago Carrillo
 * 7/11/21.
 */
class ImageBitmapLoader : Target {

    interface ImageLoaderListener {
        fun onImageLoaded(bitmap: Bitmap?)
    }

    private var imageLoaderListener: ImageLoaderListener? = null

    fun shareImageFromURI(url: String?, listener: ImageLoaderListener) {
        this.imageLoaderListener = listener
        Picasso.get().load(url).into(this)
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        imageLoaderListener?.onImageLoaded(bitmap)
    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        imageLoaderListener?.onImageLoaded(null)
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
}