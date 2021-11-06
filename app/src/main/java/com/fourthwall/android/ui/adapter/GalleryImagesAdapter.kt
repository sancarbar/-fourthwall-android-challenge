package com.fourthwall.android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.fourthwall.android.R
import com.fourthwall.android.data.entity.ImageInfo
import com.squareup.picasso.Picasso


/**
 * @author Santiago Carrillo
 * 6/11/21.
 */
class GalleryImagesAdapter(
    private val images: List<ImageInfo>,
    private val imageClickListener: ImageClickListener
) :
    RecyclerView.Adapter<GalleryImagesAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.image_card, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageInfo = images[position]
        Picasso.get()
            .load("https://picsum.photos/300?image=${imageInfo.id}")
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.broken_image)
            .into(holder.image)
        holder.itemView.setOnClickListener {
            imageClickListener.onImageClicked(imageInfo)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}