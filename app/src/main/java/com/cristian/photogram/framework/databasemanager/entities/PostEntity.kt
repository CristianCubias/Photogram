package com.cristian.photogram.framework.databasemanager.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cristian.photogram.domain.model.User

@Entity(tableName = "liked_posts")
data class PostEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @ColumnInfo(name = "photo_url")
    val photoUrl : String,
    val width: Int,
    val height: Int,
    val description : String?,
    val likes: Int,
    @ColumnInfo(name = "liked_by_user")
    val likedByUser: Boolean,
    @Embedded val user : UserEntity,
    val views : Int,
    val downloads : Int,
)

data class UserEntity(
    @ColumnInfo(name = "user_id") val userId: String,
    val username: String,
    val name: String,
    @ColumnInfo(name = "profile_picture_url") val profilePictureUrl: String?,
    val bio: String?,
    val location: String?,
    @ColumnInfo(name = "total_collections") val totalCollections: Int,
    @ColumnInfo(name = "total_likes") val totalLikes: Int,
    @ColumnInfo(name = "total_photos") val totalPhotos: Int,
)

