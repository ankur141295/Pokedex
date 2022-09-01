package com.ankur_anand.pokedex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ankur_anand.pokedex.data.remote.response.Pokemon

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemon(pokemon: Pokemon)

    @Query("SELECT * FROM pokemon_detail_table WHERE name =:name")
    suspend fun getPokemon(name: String): Pokemon?
}