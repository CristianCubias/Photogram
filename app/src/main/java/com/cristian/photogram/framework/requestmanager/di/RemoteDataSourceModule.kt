package com.cristian.photogram.framework.requestmanager.di

import com.cristian.photogram.data.RemoteDataSource
import com.cristian.photogram.framework.requestmanager.PhotogramApi
import com.cristian.photogram.framework.requestmanager.RemoteDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {
    @Provides
    @Singleton
    fun provideRemoteDataSource(api: PhotogramApi): RemoteDataSource {
        return RemoteDataSourceImp(api)
    }
}