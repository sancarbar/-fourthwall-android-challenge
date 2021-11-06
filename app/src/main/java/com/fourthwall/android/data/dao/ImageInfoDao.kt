package com.fourthwall.android.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.fourthwall.android.data.entity.ImageInfo

/**
 * @author Santiago Carrillo
 * 6/11/21.
 */
@Dao
interface ImageInfoDao {

    @Query("SELECT * from imageInfo")
    fun all(): LiveData<List<ImageInfo>>

    @Insert
    fun insert(imageInfo: ImageInfo)

    @Update
    fun update(imageInfo: ImageInfo)

    @Query("SELECT * from imageInfo WHERE id = :id")
    fun findById(id: String): ImageInfo?
}