package com.fourthwall.android.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fourthwall.android.data.dao.ImageInfoDao
import com.fourthwall.android.data.entity.ImageInfo

/**
 * @author Santiago Carrillo
 * 6/11/21.
 */
@Database(entities = [ImageInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imageInfoDao(): ImageInfoDao
}