package com.cristian.photogram.data.di

import com.cristian.photogram.data.LocalDataSource
import com.cristian.photogram.data.RemoteDataSource
import com.cristian.photogram.data.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource): Repository {
        return Repository(remoteDataSource = remoteDataSource, localDataSource = localDataSource)
    }
}