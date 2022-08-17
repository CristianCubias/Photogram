package com.cristian.photogram.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cristian.photogram.data.PostPagingSource
import com.cristian.photogram.domain.model.Post
import com.cristian.photogram.domain.model.Resource
import com.cristian.photogram.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.contracts.Returns
import kotlin.coroutines.CoroutineContext

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
    fun getPosts(): Flow<PagingData<Post>> {
        return posts
    }
    fun getLikedPosts(): Flow<PagingData<Post>> { return likedPosts }

    fun addLikedPost(post: Post): Resource<Unit> {
        return try {
            viewModelScope.launch(coroutineContext + exceptionHandler) {
                addLikedPostUseCase(post)
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun removeLikedPost(postId: String): Resource<Unit> {
        return try {
            viewModelScope.launch(coroutineContext + exceptionHandler) {
                removeLikedPostUseCase(postId)
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}