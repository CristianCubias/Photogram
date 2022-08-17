package com.cristian.photogram.framework.requestmanager

import com.cristian.photogram.data.RemoteDataSource
import com.cristian.photogram.domain.model.Post
import com.cristian.photogram.domain.model.Resource
import com.cristian.photogram.framework.requestmanager.utils.toDomain

class RemoteDataSourceImp(private val api: PhotogramApi) : RemoteDataSource {
    override suspend fun getPosts(): Resource<List<Post>> {
        val response = api.getPosts()
        return try {
            if(response.code() == 200) {
                Resource.Success(response.body()!!.map { it.toDomain() })
            } else {
                Resource.Error(Throwable(response.body()?.toString()))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}
