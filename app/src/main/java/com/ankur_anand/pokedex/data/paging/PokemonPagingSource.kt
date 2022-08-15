package com.ankur_anand.pokedex.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ankur_anand.pokedex.data.remote.ApiService
import com.ankur_anand.pokedex.data.remote.response.PokemonList
import com.ankur_anand.pokedex.utils.Constants.PAGE_SIZE
import timber.log.Timber

class PokemonPagingSource(
    private val apiService: ApiService,
) : PagingSource<Int, PokemonList.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonList.Result> {

        return try {
            val position = params.key  ?: 1
            val apiResult =
                apiService.getPokemonList(
                    limit = PAGE_SIZE,
                    offset = if (position == 1) 0 else position * PAGE_SIZE,
                )

            LoadResult.Page(
                data = apiResult.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (apiResult.results.isEmpty()) null else position.plus(1)
            )
        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonList.Result>): Int? {
        return state.anchorPosition?.let {position->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }
}