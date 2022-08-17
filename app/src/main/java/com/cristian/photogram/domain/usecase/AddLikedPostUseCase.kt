package com.cristian.photogram.domain.usecase

import com.cristian.photogram.data.Repository
import com.cristian.photogram.domain.model.Post
import com.cristian.photogram.domain.model.Resource

class AddLikedPostUseCase(private val repository: Repository){
    suspend operator fun invoke(post: Post) = repository.addLikedPost(post)
}