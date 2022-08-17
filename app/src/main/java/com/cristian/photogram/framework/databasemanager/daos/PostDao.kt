package com.cristian.photogram.framework.databasemanager.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cristian.photogram.domain.model.Post
import com.cristian.photogram.domain.model.Resource
import com.cristian.photogram.framework.databasemanager.entities.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM liked_posts LIMIT :limit OFFSET :offset")
    suspend fun getLikedPosts(limit: Int, offset: Int): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLikedPost(post: PostEntity)

    @Query("DELETE FROM liked_posts WHERE id = :postId")
    suspend fun removeLikedPost(postId: String)

    @Query("SELECT liked_by_user FROM liked_posts WHERE id = :postId ")
    suspend fun isLiked(postId: String) : Boolean

}