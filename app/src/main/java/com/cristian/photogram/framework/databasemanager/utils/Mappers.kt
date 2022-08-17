package com.cristian.photogram.framework.databasemanager.utils

import com.cristian.photogram.domain.model.Post
import com.cristian.photogram.domain.model.User
import com.cristian.photogram.framework.databasemanager.entities.PostEntity
import com.cristian.photogram.framework.databasemanager.entities.UserEntity

fun PostEntity.toDomain() =
    Post(
        id = this.id,
        createdAt = this.createdAt,
        photoUrl = this.photoUrl,
        width = this.width,
        height = this.height,
        description = this.description,
        likes = this.likes,
        likedByUser = this.likedByUser,
        user  =  this.user.toDomain(),
        views = this.views,
        downloads = this.downloads,
    )

fun UserEntity.toDomain() =
    User(
        id = this.userId,
        name = this.name,
        username = this.username,
        profilePictureUrl =  this.profilePictureUrl,
        bio = this.bio,
        location = this.location,
        totalCollections = this.totalCollections,
        totalLikes = this.totalLikes,
        totalPhotos = this.totalPhotos,
    )

fun Post.toEntity() =
    PostEntity(
        id = id,
        createdAt = createdAt,
        photoUrl = photoUrl,
        width = width,
        height = height,
        description = description,
        likes = likes,
        likedByUser = likedByUser,
        user = user.toEntity(),
        views = views,
        downloads = downloads,
    )

fun User.toEntity() =
    UserEntity(
        userId = id,
        name = name,
        username = username,
        profilePictureUrl =  profilePictureUrl,
        bio = bio,
        location = location,
        totalCollections = totalCollections,
        totalLikes = totalLikes,
        totalPhotos = totalPhotos,
    )