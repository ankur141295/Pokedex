package com.ankur_anand.pokedex.ui.screens.home

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ankur_anand.pokedex.R
import com.ankur_anand.pokedex.data.model.PokedexListEntry
import com.ankur_anand.pokedex.navigation.Screen
import com.ankur_anand.pokedex.ui.theme.LightLightBlue
import com.ankur_anand.pokedex.utils.getVerticalGradient
import com.ankur_anand.pokedex.utils.setHomeStatusBarAndIconColor
import com.ankur_anand.pokedex.utils.supportsDynamicColor

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val searchText = viewModel.searchText

    val view = LocalView.current

    if (!view.isInEditMode) {
        val statusBarColor =
            if (supportsDynamicColor()) MaterialTheme.colorScheme.background else LightLightBlue

        SideEffect {
            (view.context as Activity).setHomeStatusBarAndIconColor(
                view = view,
                darkTheme = darkTheme,
                statusBarColor = statusBarColor
            )
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))

            PokemonLogo()

            Spacer(modifier = Modifier.height(16.dp))

            SearchBar(
                value = searchText.value,
                onValueChange = viewModel::setSearchText,
                onDonePressed = viewModel::getSearchResult,
                clearSearchText = viewModel::clearSearchText
            )

            Spacer(modifier = Modifier.height(8.dp))

            PokemonList(
                navController = navController,
                searchText = searchText.value
            )
        }
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun PokemonList(
    navController: NavHostController,
    searchText: TextFieldValue,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val pokemonList = viewModel.pokemonData.collectAsLazyPagingItems()
    val searchList = viewModel.searchResult.collectAsStateWithLifecycle()

    val onCardClicked: (PokedexListEntry, Color) -> Unit = { pokemon, dominantColor ->
        navController.navigate(
            Screen.DetailScreen.route(
                pokemonName = pokemon.pokemonName,
                dominantColor = dominantColor.toArgb()
            )
        )
    }

    Column {
        AnimatedVisibility(visible = searchText.text.isBlank()) {
            LazyVerticalGrid(
                modifier = modifier.padding(horizontal = 8.dp),
                columns = GridCells.Fixed(count = 2)
            ) {
                items(count = pokemonList.itemCount, key = { it }) { index ->
                    pokemonList[index]?.let { pokemon ->
                        PokemonCard(pokemon = pokemon, onClick = onCardClicked)
                    }
                }
            }
        }

        AnimatedVisibility(visible = searchText.text.isNotBlank()) {
            LazyVerticalGrid(
                modifier = modifier.padding(horizontal = 8.dp),
                columns = GridCells.Fixed(count = 2)
            ) {
                items(count = searchList.value.size, key = { it }) { index ->
                    PokemonCard(pokemon = searchList.value[index], onClick = onCardClicked)
                }
            }
        }
    }
}

@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    pokemon: PokedexListEntry,
    onClick: (PokedexListEntry, Color) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface

    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(pokemon.imageUrl)
            .crossfade(true)
            .build()
    )

    Surface(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .aspectRatio(1f)
                .background(getVerticalGradient(dominantColor = dominantColor))
                .clickable { onClick(pokemon, dominantColor) }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painter,
                        contentDescription = pokemon.pokemonName,
                    )

                    when (painter.state) {
                        AsyncImagePainter.State.Empty -> {
                            /*Not required as of now*/
                        }
                        is AsyncImagePainter.State.Loading -> {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .scale(0.5f)
                            )
                        }
                        is AsyncImagePainter.State.Success -> {
                            val drawable =
                                (painter.state as? AsyncImagePainter.State.Success)?.result?.drawable

                            viewModel.calculateDominateColor(drawable = drawable) {
                                dominantColor = it
                            }
                        }
                        is AsyncImagePainter.State.Error -> {
                            /*Not required as of now*/
                        }
                    }
                }
                Text(
                    text = pokemon.pokemonName,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.background
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: TextFieldValue = TextFieldValue(""),
    onValueChange: (TextFieldValue) -> Unit,
    onDonePressed: () -> Unit,
    clearSearchText: () -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            maxLines = 1,
            placeholder = {
                Text(text = stringResource(R.string.search))
            },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDonePressed()
                    keyboardController?.hide()
                }
            ),
            trailingIcon = {
                if (value.text.isNotEmpty()) {
                    IconButton(
                        onClick = clearSearchText
                    ) {
                        Icon(
                            Icons.Rounded.Clear,
                            contentDescription = stringResource(R.string.clear_search)
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun PokemonLogo() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
            contentDescription = stringResource(R.string.pokemon_logo)
        )
    }
}

/*
//           Timber.e("${pokemonList.loadState.source}")
//           Timber.e("${pokemonList.loadState.mediator}")

//            when(pokemonList.loadState.prepend){
//                is LoadState.Error -> {
//                    Timber.e("Prepend : Error")
//                }
//                is LoadState.Loading -> {
//                    Timber.e("Prepend : Loading")
//                }
//                is LoadState.NotLoading -> {
//                    Timber.e("Prepend : Not Loading")
//                }
//            }
//
//            when (pokemonList.loadState.append) {
//                is LoadState.Error -> {
//                    Timber.e("Append : Error")
//                    val e = pokemonList.loadState.append as LoadState.Error
//
////                    this@LazyVerticalGrid.item {
////                        ErrorItem(
////                            message = e.error.localizedMessage!!,
////                            onClickRetry = { pokemonList.retry() }
////                        )
////                    }
//                }
//                is LoadState.Loading -> {
//                    Timber.e("Append : Loading")
//
//                    this@LazyColumn.item {
//                        LoadingItem()
//                    }
//                }
//                is LoadState.NotLoading -> {
//                    Timber.e("Append : Not Loading")
//                    Unit
//                }
//            }
//
//            when (pokemonList.loadState.refresh) {
//                is LoadState.Error -> {
//                    Timber.e("Refresh : Error")
//                    val e = pokemonList.loadState.refresh as LoadState.Error
//
////                    this@LazyVerticalGrid.item {
////                        ErrorItem(
////                            message = e.error.localizedMessage!!,
////                            modifier = Modifier.fillMaxSize(),
////                            onClickRetry = { pokemonList.retry() }
////                        )
////                    }
//                }
//                is LoadState.Loading -> {
//                    Timber.e("Refresh : Loading")
//
//
////                    this@LazyVerticalGrid.item {
////                        LoadingView(modifier = Modifier.fillMaxSize())
////                    }
//                }
//                is LoadState.NotLoading -> {
//                    Timber.e("Refresh : Not Loading")
//                    Unit
//                }
//            }
* */
