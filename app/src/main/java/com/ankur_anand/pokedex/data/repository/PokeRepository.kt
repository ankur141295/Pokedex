package com.ankur_anand.pokedex.data.repository

import androidx.paging.PagingData
import com.ankur_anand.pokedex.data.model.PokedexListEntry
import com.ankur_anand.pokedex.data.remote.response.Pokemon
import com.ankur_anand.pokedex.data.remote.response.PokemonList
import com.ankur_anand.pokedex.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface PokeRepository {

//    fun getPokemonList(): Flow<PagingData<PokemonList.Result>>

    suspend fun getSearchResult(searchQuery: String): List<PokedexListEntry>

    fun getPokemonList(): Flow<PagingData<PokedexListEntry>>

    suspend fun getPokemonInfo(pokemonName: String): ApiResponse<Pokemon>

}