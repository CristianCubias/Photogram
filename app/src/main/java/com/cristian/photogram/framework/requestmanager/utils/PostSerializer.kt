package com.cristian.photogram.framework.requestmanager.utils

import com.squareup.moshi.Json


data class PostSerializer(
    @field: Json(name = "id")
    val id: String,

    @field: Json(name = "created_at")
    val createdAt: String,

    @field: Json(name = "urls")
    val urls : UrlSerializer,

    @field: Json(name = "width")
    val width: Int,

    @field: Json(name = "height")
    val height: Int,

    @field: Json(name = "description")
    val description: String?,

    @field: Json(name = "likes")
    val likes: Int,

    @field: Json(name = "user")
    val user : UserSerializer,

    @field: Json(name = "views")
    val views : Int,

    @field: Json(name = "downloads")
    val downloads : Int,
)

data class UserSerializer(
    @field: Json(name = "id")
    val id: String,

    @field: Json(name = "username")
    val username: String,

    @field: Json(name = "name")
    val name: String,

    @field: Json(name = "profile_image")
    val profileImage: ProfileImageSerializer,

    @field: Json(name = "bio")
    val bio: String?,

    @field: Json(name = "location")
    val location: String?,

    @field: Json(name = "total_collections")
    val totalCollections: Int,

    @field: Json(name = "total_likes")
    val totalLikes: Int,

    @field: Json(name = "total_photos")
    val totalPhotos: Int,
)

data class UrlSerializer(
    @field: Json(name = "regular")
    val regular: String,
)

data class ProfileImageSerializer(
    @field: Json(name = "medium")
    val regular: String,
)