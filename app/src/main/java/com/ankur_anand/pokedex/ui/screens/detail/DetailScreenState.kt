package com.ankur_anand.pokedex.ui.screens.detail

import com.ankur_anand.pokedex.data.remote.response.Pokemon

sealed class DetailScreenState {
    data class IsLoading(var isLoading: Boolean) : DetailScreenState()
    data class PokemonData(val pokemon: Pokemon) : DetailScreenState()
    data class Error(val message: String) : DetailScreenState()
}
