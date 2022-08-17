package com.cristian.photogram.data

import com.cristian.photogram.domain.model.Post
import com.cristian.photogram.domain.model.Resource

interface RemoteDataSource {
    suspend fun getPosts(): Resource<List<Post>>
}