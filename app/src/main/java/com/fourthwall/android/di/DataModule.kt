package com.fourthwall.android.di

import android.content.Context
import androidx.room.Room
import com.fourthwall.android.data.AppDatabase
import com.fourthwall.android.data.dao.ImageInfoDao
import com.fourthwall.android.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Santiago Carrillo
 * 6/11/21.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideImageInfoDao(appDatabase: AppDatabase): ImageInfoDao {
        return appDatabase.imageInfoDao()
    }
}