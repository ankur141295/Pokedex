package com.ankur_anand.pokedex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ankur_anand.pokedex.utils.Constants.POKEMON_LIST_REMOTE_KEY_TABLE

@Entity(tableName = POKEMON_LIST_REMOTE_KEY_TABLE)
data class PokedexListRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)