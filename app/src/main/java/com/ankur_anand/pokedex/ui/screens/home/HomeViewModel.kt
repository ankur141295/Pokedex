package com.ankur_anand.pokedex.ui.screens.home

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.palette.graphics.Palette
import com.ankur_anand.pokedex.data.model.PokedexListEntry
import com.ankur_anand.pokedex.data.repository.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PokeRepository,
) : ViewModel() {

    private val _searchResult = MutableStateFlow<List<PokedexListEntry>>(emptyList())
    val searchResult = _searchResult.asStateFlow()

    private val _searchText = mutableStateOf(TextFieldValue(""))
    val searchText: State<TextFieldValue> = _searchText

    var pokemonData = repository.getPokemonList().cachedIn(viewModelScope)

/*//    init {
//        pokemonData = repository.getPokemonList().cachedIn(viewModelScope)
//            .map { pagingData ->
//
//                pagingData.map { entry ->
//                    val number = if (entry.url.endsWith("/")) {
//                        entry.url.dropLast(1).takeLastWhile { it.isDigit() }
//                    } else {
//                        entry.url.takeLastWhile { it.isDigit() }
//                    }
//
//                    val url =
//                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$number.png"
//
//                    PokedexListEntry(
//                        pokemonName = entry.name.capitalizeFirstLetter(),
//                        imageUrl = url,
//                        number = number.toInt()
//                    )
//                }
//            }
//    }*/

    fun calculateDominateColor(drawable: Drawable?, onFinish: (Color) -> Unit) {
        if (drawable != null) {
            val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

            Palette.from(bmp).generate { palette ->
                palette?.dominantSwatch?.rgb?.let { colorValue ->
                    onFinish(Color(colorValue))
                }
            }
        }
    }

    fun getSearchResult() {
        if (_searchText.value.text.isNotBlank()) {
            viewModelScope.launch {
                _searchResult.value = emptyList()
                _searchResult.value = repository.getSearchResult(_searchText.value.text)
            }
        }
    }

    fun setSearchText(searchText: TextFieldValue) {
        _searchText.value = searchText
        if (_searchText.value.text.isBlank()) {
            _searchResult.value = emptyList()
        } else {
            getSearchResult()
        }
    }

    fun clearSearchText() {
        _searchResult.value = emptyList()
        _searchText.value = TextFieldValue("")
    }
}