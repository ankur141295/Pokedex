package com.ankur_anand.pokedex.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankur_anand.pokedex.data.repository.PokeRepository
import com.ankur_anand.pokedex.navigation.NAVIGATION_ARGUMENT_POKEMON_NAME
import com.ankur_anand.pokedex.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "DetailScreenViewModel"

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val repository: PokeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState by mutableStateOf<DetailScreenState>(DetailScreenState.IsLoading(true))
        private set

    private val pokemonName: String

    init {
        pokemonName = savedStateHandle.get<String>(NAVIGATION_ARGUMENT_POKEMON_NAME) ?: ""
        getPokemonData()
    }


    private fun getPokemonData() {
        viewModelScope.launch {
            uiState = when (val response = repository.getPokemonInfo(pokemonName.lowercase())) {
                is ApiResponse.Success -> {
                    DetailScreenState.PokemonData(response.data)
                }
                is ApiResponse.Error -> {
                    DetailScreenState.Error(response.message)
                }
            }
        }
    }

    fun retry() {
        getPokemonData()
    }
}