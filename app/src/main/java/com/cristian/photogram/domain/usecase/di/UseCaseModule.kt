package com.cristian.photogram.domain.usecase.di

import com.cristian.photogram.data.Repository
import com.cristian.photogram.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideGetPostsUseCase(repository: Repository): GetPostsFromRemoteUseCase {
        return GetPostsFromRemoteUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideAddLikedPostUseCase(repository: Repository): AddLikedPostUseCase {
        return AddLikedPostUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetLikedPostsUseCase(repository: Repository): GetLikedPostsUseCase {
        return GetLikedPostsUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideRemoveLikedPostUseCase(repository: Repository): RemoveLikedPostUseCase {
        return RemoveLikedPostUseCase(repository)
    }
}