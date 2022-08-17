package com.cristian.photogram.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cristian.photogram.domain.model.Post
import com.cristian.photogram.domain.model.Resource

class LikedPostPagingSource(private val localDataSource: LocalDataSource) : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val pageNumber = params.key ?: 0
            when (val response = localDataSource.getLikedPosts(limit = params.loadSize, offset = (pageNumber * params.loadSize))) {
                is Resource.Success -> LoadResult.Page(
                    data = response.data,
                    prevKey = if (pageNumber == 0) null else pageNumber - 1,
                    nextKey = if (response.data.isEmpty()) null else pageNumber + 1
                )
                is Resource.Error -> LoadResult.Error(response.error)
                else -> { LoadResult.Error(Exception("Unknown error")) }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}