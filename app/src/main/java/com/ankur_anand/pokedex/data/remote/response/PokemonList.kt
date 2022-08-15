package com.ankur_anand.pokedex.data.remote.response

import com.squareup.moshi.Json

data class PokemonList(
    @Json(name = "count")
    val count: Int,
    @Json(name = "next")
    val next: String,
    @Json(name = "previous")
    val previous: Any?,
    @Json(name = "results")
    val results: List<Result>
) {
    data class Result(
        @Json(name = "name")
        val name: String,
        @Json(name = "url")
        val url: String
    )
}