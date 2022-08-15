package com.ankur_anand.pokedex.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ankur_anand.pokedex.data.model.PokedexListEntry

@Dao
interface PokedexListEntryDao {

    @Query("SELECT * FROM pokemon_list_table")
    fun getAllPokemons(): PagingSource<Int, PokedexListEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemons(pokemon: List<PokedexListEntry>)

    @Query("DELETE FROM pokemon_list_table")
    suspend fun deleteAllPokemons()

    @Query(
        "SELECT * FROM pokemon_list_table WHERE pokemonName LIKE '%' || :searchQuery || '%'"
                + "OR number LIKE '%' || :searchQuery || '%'"
    )
    suspend fun searchPokemon(searchQuery: String): List<PokedexListEntry>

}