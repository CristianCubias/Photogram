package com.cristian.photogram.domain.usecase

import com.cristian.photogram.data.Repository

class RemoveLikedPostUseCase(private val repository: Repository) {
    suspend operator fun invoke(postId: String) = repository.removeLikedPost(postId)
}