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
import com.ankur_anand.pokedex.utils.ApiResponse
import com.ankur_anand.pokedex.utils.Constants.PAGE_SIZE
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@ActivityScoped
class PokeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val pokemonDatabase: PokemonDatabase
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
        TODO("Not yet implemented")
    }
}