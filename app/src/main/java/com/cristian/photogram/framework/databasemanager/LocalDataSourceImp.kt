package com.cristian.photogram.framework.databasemanager

import com.cristian.photogram.data.LocalDataSource
import com.cristian.photogram.domain.model.Post
import com.cristian.photogram.domain.model.Resource
import com.cristian.photogram.framework.databasemanager.utils.toDomain
import com.cristian.photogram.framework.databasemanager.utils.toEntity

class LocalDataSourceImp(private val database: PostDatabase): LocalDataSource {
    override suspend fun addLikedPost(post: Post): Resource<Unit> {
        return try {
            database.postDao().addLikedPost(post.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun isLiked(postID: String): Resource<Boolean>{
        return try {
            val result = database.postDao().isLiked(postID)
            Resource.Success(result)
        } catch (e: Exception) {
            println(e)
            Resource.Error(e)
        }
    }

    override suspend fun removeLikedPost(id: String): Resource<Unit> {
        return try {
            database.postDao().removeLikedPost(id)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getLikedPosts(limit: Int, offset: Int): Resource<List<Post>> {
        return try {
            val result = database.postDao().getLikedPosts(limit, offset).map { it.toDomain() }
            Resource.Success(result)
        }
        catch (e: Exception) {
            Resource.Error(e)
        }
    }
}