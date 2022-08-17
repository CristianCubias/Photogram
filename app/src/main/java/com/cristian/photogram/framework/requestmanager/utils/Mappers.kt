package com.cristian.photogram.framework.requestmanager.utils

import com.cristian.photogram.domain.model.Post
import com.cristian.photogram.domain.model.User

fun PostSerializer.toDomain() =
    Post(
        id = id,
        createdAt = createdAt,
        photoUrl = urls.regular,
        width = width,
        height = height,
        description = description,
        likes = likes,
        likedByUser = false,
        user = user.toDomain(),
        views = views,
        downloads = downloads,
    )

fun UserSerializer.toDomain() =
    User(
        id = id,
        name = name,
        username = username,
        profilePictureUrl =  profileImage.regular,
        bio = bio,
        location = location,
        totalCollections = totalCollections,
        totalLikes = totalLikes,
        totalPhotos = totalPhotos,
    )