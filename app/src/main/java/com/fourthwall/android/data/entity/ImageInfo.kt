package com.fourthwall.android.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fourthwall.android.network.dto.ImageDto

/**
 * @author Santiago Carrillo
 * 6/11/21.
 */
@Entity
data class ImageInfo(
    @PrimaryKey(autoGenerate = true) val oid: Int?,
    var id: String,
    var author: String,
    var width: String,
    var height: String,
    var url: String,
    var downloadUrl: String
) {
    fun update(imageDto: ImageDto) {
        author = imageDto.author
        width = imageDto.width
        height = imageDto.height
        url = imageDto.url
        downloadUrl = imageDto.download_url
    }

    constructor(imageDto: ImageDto) : this(
        null,
        imageDto.id,
        imageDto.author,
        imageDto.width,
        imageDto.height,
        imageDto.url,
        imageDto.download_url
    )
}
