package com.ankur_anand.pokedex.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ankur_anand.pokedex.data.local.PokemonDatabase
import com.ankur_anand.pokedex.data.model.PokedexListEntry
import com.ankur_anand.pokedex.data.model.PokedexListRemoteKeys
import com.ankur_anand.pokedex.data.remote.ApiService
import com.ankur_anand.pokedex.utils.Constants.PAGE_SIZE
import com.ankur_anand.pokedex.utils.capitalizeFirstLetter

@ExperimentalPagingApi
class PokedexListRemoteMediator(
    private val apiService: ApiService,
    private val pokemonDatabase: PokemonDatabase
) : RemoteMediator<Int, PokedexListEntry>() {

    private val pokedexListEntryDao = pokemonDatabase.pokedexLisEntrytDao()
    private val pokedexListEntryRemoteKeysDao = pokemonDatabase.pokedexListRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokedexListEntry>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = apiService.getPokemonList(
                limit = PAGE_SIZE,
                offset = if (currentPage == 1) 0 else currentPage * PAGE_SIZE,
            )
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            pokemonDatabase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    pokedexListEntryDao.deleteAllPokemons()
                    pokedexListEntryRemoteKeysDao.deleteAllRemoteKeys()
                }

                val pokedexListEntry = response.results.map { entry ->
                    val number = if (entry.url.endsWith("/")) {
                        entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                    } else {
                        entry.url.takeLastWhile { it.isDigit() }
                    }

                    val url =
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$number.png"

                    PokedexListEntry(
                        pokemonName = entry.name.capitalizeFirstLetter(),
                        imageUrl = url,
                        number = number.toInt()
                    )
                }

                val keys = pokedexListEntry.map {
                    PokedexListRemoteKeys(
                        id = it.number.toString(),
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                pokedexListEntryRemoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                pokedexListEntryDao.addPokemons(pokemon = pokedexListEntry)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PokedexListEntry>
    ): PokedexListRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.number?.let { id ->
                pokedexListEntryRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PokedexListEntry>
    ): PokedexListRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { pokedex ->
                pokedexListEntryRemoteKeysDao.getRemoteKeys(id = pokedex.number.toString())
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PokedexListEntry>
    ): PokedexListRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { pokedex ->
                pokedexListEntryRemoteKeysDao.getRemoteKeys(id = pokedex.number.toString())
            }
    }

}