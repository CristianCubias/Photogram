package com.cristian.photogram.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cristian.photogram.domain.model.Post
import com.cristian.photogram.domain.model.Resource
import com.cristian.photogram.domain.usecase.AddLikedPostUseCase
import com.cristian.photogram.domain.usecase.GetLikedPostsUseCase
import com.cristian.photogram.domain.usecase.GetPostsFromRemoteUseCase
import com.cristian.photogram.domain.usecase.RemoveLikedPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getPostsFromRemoteUseCase: GetPostsFromRemoteUseCase,
    private val addLikedPostUseCase: AddLikedPostUseCase,
    private val removeLikedPostUseCase: RemoveLikedPostUseCase,
    getLikedPostsUseCase: GetLikedPostsUseCase,
) : ViewModel() {
    private val coroutineContext = Dispatchers.IO
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    private val posts = getPostsFromRemoteUseCase(coroutineContext + exceptionHandler).cachedIn(viewModelScope)
    private val likedPosts = getLikedPostsUseCase(coroutineContext + exceptionHandler).cachedIn(viewModelScope)
    fun getPosts(): Flow<PagingData<Post>> { return posts }
    fun getLikedPosts(): Flow<PagingData<Post>> { return likedPosts }

    suspend fun addLikedPost(post: Post): Resource<Unit> {
        return flow { emit(addLikedPostUseCase(post)) }.flowOn(coroutineContext).first()
    }

    suspend fun removeLikedPost(postId: String): Resource<Unit> {
        return flow { emit(removeLikedPostUseCase(postId)) }.flowOn(coroutineContext).first()
    }
}