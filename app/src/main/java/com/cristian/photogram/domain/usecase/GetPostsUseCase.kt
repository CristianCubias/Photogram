package com.cristian.photogram.domain.usecase

import com.cristian.photogram.data.Repository
import kotlin.coroutines.CoroutineContext

class GetPostsFromRemoteUseCase(private val repository: Repository) {
    operator fun invoke(coroutineContext: CoroutineContext) = repository.getPostsFromRemote(coroutineContext)
}