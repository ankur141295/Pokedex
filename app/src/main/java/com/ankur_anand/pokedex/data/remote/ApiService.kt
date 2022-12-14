package com.ankur_anand.pokedex.data.remote

import com.ankur_anand.pokedex.data.remote.response.Pokemon
import com.ankur_anand.pokedex.data.remote.response.PokemonList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit : Int,
        @Query("offset") offset: Int,
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name : String
    ): Response<Pokemon>
}