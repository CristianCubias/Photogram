package com.cristian.photogram.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.cristian.photogram.domain.model.Post
import com.cristian.photogram.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun addLikedPost(post: Post): Resource<Unit>
    suspend fun removeLikedPost(id: String): Resource<Unit>
    suspend fun isLiked(postID: String): Resource<Boolean>
    suspend fun getLikedPosts(limit: Int, offset: Int): Resource<List<Post>>
}