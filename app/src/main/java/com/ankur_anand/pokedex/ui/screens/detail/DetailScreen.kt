package com.ankur_anand.pokedex.ui.screens.detail

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ankur_anand.pokedex.R
import com.ankur_anand.pokedex.data.remote.response.Pokemon
import com.ankur_anand.pokedex.data.remote.response.Pokemon.Type
import com.ankur_anand.pokedex.ui.screens.detail.DetailScreenState.PokemonData
import com.ankur_anand.pokedex.utils.capitalizeFirstLetter
import com.ankur_anand.pokedex.utils.parseTypeToColor
import com.ankur_anand.pokedex.utils.setDetailStatusBarAndIconColor

@Composable
fun DetailScreen(
    navController: NavHostController,
    pokemonName: String,
    dominantColor: Color,
    viewModel: DetailScreenViewModel = hiltViewModel(),
) {

    val onBackPressed: () -> Unit = {
        navController.navigateUp()
    }
    var openErrorDialog by remember { mutableStateOf(false) }

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).setDetailStatusBarAndIconColor(
                view = view,
                dominantColor = dominantColor,
            )
        }
    }

    Box(
        modifier = Modifier
            .background(Color.White)
            .background(dominantColor.copy(0.5f))
            .fillMaxSize(),
    ) {
        Column {

            AppBar(onBackPressed = onBackPressed)

            Spacer(modifier = Modifier.height(64.dp))

            when (viewModel.uiState) {
                is DetailScreenState.Error -> {
                    openErrorDialog = true

                    ShowErrorDialog(
                        openDialog = openErrorDialog,
                        dismissButtonClicked = onBackPressed,
                        confirmButtonClicked = {
                            openErrorDialog = false
                            viewModel.retry()
                        },
                        errorMessage = (viewModel.uiState as DetailScreenState.Error).message
                    )
                }
                is DetailScreenState.IsLoading -> CircularLoader()
                is PokemonData -> {
                    PokemonCard(
                        pokemon = (viewModel.uiState as PokemonData).pokemon
                    )
                }
            }
        }
    }


}

@Composable
fun ShowErrorDialog(
    openDialog: Boolean,
    errorMessage: String,
    dismissButtonClicked: () -> Unit,
    confirmButtonClicked: () -> Unit,
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(text = stringResource(R.string.unable_to_fetch_data))
            },
            text = {
                Text(errorMessage)
            },
            confirmButton = {
                TextButton(
                    onClick = confirmButtonClicked
                ) {
                    Text(stringResource(R.string.retry))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = dismissButtonClicked
                ) {
                    Text(stringResource(R.string.go_back))
                }
            }
        )

    }
}

@Composable
fun PokemonCard(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 24.dp, start = 24.dp, bottom = 24.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = stringResource(
                        id = R.string.pokemon_id_name,
                        pokemon.id,
                        pokemon.name.capitalizeFirstLetter()
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                PokemonType(pokemon.types)
            }
        }

        PokemonImage(
            id = pokemon.id,
            name = pokemon.name.capitalizeFirstLetter(),
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun PokemonType(
    types: List<Type>,
    modifier: Modifier = Modifier
) {

    val scrollState = rememberScrollState()

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 16.dp)
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .height(35.dp)
            ) {
                Text(
                    text = type.type.name.capitalizeFirstLetter(),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
private fun PokemonImage(
    modifier: Modifier,
    id: Int,
    name: String
) {
    AsyncImage(
        modifier = modifier
            .height(150.dp)
            .width(150.dp)
            .offset(y = -(85).dp),
        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png",
        contentDescription = stringResource(
            id = R.string.pokemon_image, name
        )
    )
}

@Composable
fun CircularLoader(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(60.dp)
        )
    }
}

@Composable
fun AppBar(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = onBackPressed
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = stringResource(R.string.back_button),
            tint = Color.Black
        )
    }
}
