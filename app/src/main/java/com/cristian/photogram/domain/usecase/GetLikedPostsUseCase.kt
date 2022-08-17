package com.cristian.photogram.domain.usecase

import com.cristian.photogram.data.Repository
import kotlin.coroutines.CoroutineContext

class GetLikedPostsUseCase(private val repository: Repository) {
    operator fun invoke(coroutineContext: CoroutineContext) = repository.getLikedPostsFromLocal(coroutineContext)
}
