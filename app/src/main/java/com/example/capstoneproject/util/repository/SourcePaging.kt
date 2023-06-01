package com.example.capstoneproject.util.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.capstoneproject.util.api.ApiService
import com.example.capstoneproject.util.api.RecipesItem

class SourcePaging(
    private val apiService: ApiService,
) : PagingSource<Int, RecipesItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecipesItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getRecipes(page, params.loadSize).listRecipes
            LoadResult.Page(
                data = responseData,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RecipesItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
