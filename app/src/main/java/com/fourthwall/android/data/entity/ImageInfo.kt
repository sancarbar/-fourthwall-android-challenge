package com.fourthwall.android.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Santiago Carrillo
 * 6/11/21.
 */
@Entity
data class ImageInfo(
    @PrimaryKey(autoGenerate = true) val oid: Int?,
    var id: String,
    val author: String,
    val width: String,
    val height: String,
    val url: String,
    val downloadUrl: String
)
