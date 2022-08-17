package com.cristian.photogram.framework.databasemanager.di

import com.cristian.photogram.data.LocalDataSource
import com.cristian.photogram.framework.databasemanager.LocalDataSourceImp
import com.cristian.photogram.framework.databasemanager.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {
    @Provides
    @Singleton
    fun provideLocalDataSource(db: PostDatabase): LocalDataSource {
        return LocalDataSourceImp(db)
    }
}