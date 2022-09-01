package com.ankur_anand.pokedex.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ankur_anand.pokedex.data.local.PokemonDatabase
import com.ankur_anand.pokedex.data.model.PokedexListEntry
import com.ankur_anand.pokedex.data.paging.PokedexListRemoteMediator
import com.ankur_anand.pokedex.data.remote.ApiService
import com.ankur_anand.pokedex.data.remote.response.Pokemon
import com.ankur_anand.pokedex.di.IoDispatcher
import com.ankur_anand.pokedex.utils.ApiResponse
import com.ankur_anand.pokedex.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PokeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val pokemonDatabase: PokemonDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : PokeRepository {

    override fun getPokemonList(): Flow<PagingData<PokedexListEntry>> {
        val pagingSourceFactory =
            { pokemonDatabase.pokedexLisEntrytDao().getAllPokemons() }

        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = PokedexListRemoteMediator(
                apiService = apiService,
                pokemonDatabase = pokemonDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun getSearchResult(searchQuery: String): List<PokedexListEntry> {
        return pokemonDatabase.pokedexLisEntrytDao().searchPokemon(searchQuery)
    }

    override suspend fun getPokemonInfo(pokemonName: String): ApiResponse<Pokemon> {

//        val dbResponse = getPokemonFromDb(pokemonName = pokemonName)
//
//        if (dbResponse != null) {
//            return ApiResponse.Success(data = dbResponse)
//        }

        try {
            val apiResponse = apiService.getPokemonInfo(name = pokemonName)

            if (apiResponse.isSuccessful) {

                apiResponse.body()?.let { pokemon ->
//                    pokemonDatabase.pokemonDao().addPokemon(pokemon)

                    return ApiResponse.Success(data = pokemon)

//                    val pokemonData = getPokemonFromDb(pokemonName)
//
//                    pokemonData?.let {
//                        return ApiResponse.Success(data = it)
//                    } ?: return ApiResponse.Error(message = "Something went wrong")

                } ?: return ApiResponse.Error(message = apiResponse.message())
            } else {
                return ApiResponse.Error(message = apiResponse.message())
            }
        } catch (e: Exception) {
            return ApiResponse.Error(message = e.message ?: "Something went wrong", exception = e)
        }
    }

//    private suspend fun getPokemonFromDb(pokemonName: String): Pokemon? {
//        return withContext(ioDispatcher) {
//            pokemonDatabase.pokemonDao().getPokemon(name = pokemonName)
//        }
//    }

}