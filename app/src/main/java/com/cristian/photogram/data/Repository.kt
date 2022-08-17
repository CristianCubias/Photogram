package com.cristian.photogram.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cristian.photogram.domain.model.Post
import com.cristian.photogram.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

class Repository(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource) {

    fun getPostsFromRemote(coroutineContext: CoroutineContext) : Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 15),
            initialKey = 0,
            pagingSourceFactory = { PostPagingSource(remoteDataSource, localDataSource) }
        ).flow.flowOn(coroutineContext)
    }

    fun getLikedPostsFromLocal(coroutineContext: CoroutineContext) : Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 15, initialLoadSize = 15),
            initialKey = 0,
            pagingSourceFactory = { LikedPostPagingSource(localDataSource) }
        ).flow.flowOn(coroutineContext)
    }

    suspend fun addLikedPost(post: Post): Resource<Unit> {
        return localDataSource.addLikedPost(post)
    }

    suspend fun removeLikedPost(postId: String): Resource<Unit> {
        return localDataSource.removeLikedPost(postId)
    }
}