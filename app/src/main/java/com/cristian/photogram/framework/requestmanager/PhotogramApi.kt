package com.cristian.photogram.framework.requestmanager


import com.cristian.photogram.BuildConfig
import com.cristian.photogram.framework.requestmanager.utils.PostSerializer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface PhotogramApi {
     @Headers("Authorization: Client-ID ${BuildConfig.UNSPLASH_API_KEY}")
     @GET("/photos/random?count=15")
     suspend fun getPosts() : Response<List<PostSerializer>>
}