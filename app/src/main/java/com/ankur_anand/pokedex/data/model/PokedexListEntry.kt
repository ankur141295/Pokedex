package com.ankur_anand.pokedex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ankur_anand.pokedex.utils.Constants.POKEMON_LIST_TABLE

@Entity(tableName = POKEMON_LIST_TABLE)
data class PokedexListEntry(
    val pokemonName: String,
    val imageUrl: String,
    @PrimaryKey(autoGenerate = false)
    val number: Int
)