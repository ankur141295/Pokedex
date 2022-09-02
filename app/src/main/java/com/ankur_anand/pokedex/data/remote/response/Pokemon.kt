package com.ankur_anand.pokedex.data.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ankur_anand.pokedex.utils.Constants.POKEMON_DETAIL_TABLE
import com.squareup.moshi.Json

@Entity(tableName = POKEMON_DETAIL_TABLE)
data class Pokemon(

    @Json(name = "height")
    val height: Int,

    @PrimaryKey(autoGenerate = false)
    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "stats")
    val stats: List<Stat>,

    @Json(name = "types")
    val types: List<Type>,

    @Json(name = "weight")
    val weight: Int
) {

    data class Stat(
        @Json(name = "base_stat")
        val baseStat: Int,
        @Json(name = "effort")
        val effort: Int,
        @Json(name = "stat")
        val stat: StatX
    ) {
        data class StatX(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )
    }

    data class Type(
        @Json(name = "slot")
        val slot: Int,
        @Json(name = "type")
        val type: TypeX
    ) {
        data class TypeX(
            @Json(name = "name")
            val name: String,
            @Json(name = "url")
            val url: String
        )
    }
}