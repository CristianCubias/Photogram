package com.cristian.photogram.domain.model

data class User(
    val id: String,
    val username: String,
    val name: String,
    val profilePictureUrl: String?,
    val bio: String?,
    val location: String?,
    val totalCollections: Int,
    val totalLikes: Int,
    val totalPhotos: Int,
)