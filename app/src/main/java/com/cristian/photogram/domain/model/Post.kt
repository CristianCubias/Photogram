package com.cristian.photogram.domain.model

data class Post(
    val id: String,
    val createdAt: String,
    val photoUrl : String,
    val width: Int,
    val height: Int,
    val description : String?,
    val likes: Int,
    var likedByUser: Boolean,
    val user : User,
    val views : Int,
    val downloads : Int,
)