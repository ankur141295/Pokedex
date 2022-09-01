package com.ankur_anand.pokedex.ui.screens.detail

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ankur_anand.pokedex.R
import com.ankur_anand.pokedex.data.remote.response.Pokemon
import com.ankur_anand.pokedex.data.remote.response.Pokemon.Type
import com.ankur_anand.pokedex.ui.screens.detail.DetailScreenState.PokemonData
import com.ankur_anand.pokedex.utils.*

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

            Spacer(modifier = Modifier.height(100.dp))

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
                .fillMaxWidth()
                .padding(end = 24.dp, start = 24.dp, bottom = 24.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(48.dp))

                PokemonName(name = pokemon.name.capitalizeFirstLetter(), id = pokemon.id)

                Spacer(modifier = Modifier.height(16.dp))

                PokemonType(pokemon.types)

                Spacer(modifier = Modifier.height(16.dp))

                PokemonDetailData(
                    pokemonHeight = pokemon.height,
                    pokemonWeight = pokemon.weight
                )

                Spacer(modifier = Modifier.height(16.dp))

                PokemonBaseStats(pokemonInfo = pokemon)

                Spacer(modifier = Modifier.height(16.dp))
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
fun PokemonDetailData(
    pokemonHeight: Int,
    pokemonWeight: Int,
    modifier: Modifier = Modifier
) {

    //hectogram to kilogram
    val pokemonWeightInKg = remember {
        pokemonWeight / 10f
    }

    //decimeters to meter
    val pokemonHeightInMeter = remember {
        pokemonHeight / 10f
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        PokemonDetailItem(
            detailValue = pokemonWeightInKg,
            detailUnit = stringResource(R.string.unit_kilogram),
            icon = painterResource(id = R.drawable.ic_weight),
        )

        Spacer(
            modifier = Modifier
                .size(1.dp, 70.dp)
                .background(Color.LightGray)
        )

        PokemonDetailItem(
            detailValue = pokemonHeightInMeter,
            detailUnit = stringResource(R.string.unit_meter),
            icon = painterResource(id = R.drawable.ic_height),
        )
    }


}

@Composable
fun PokemonDetailItem(
    detailValue: Float,
    detailUnit: String,
    icon: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = if (detailUnit == "Kg") stringResource(id = R.string.pokemon_weight)
            else stringResource(id = R.string.pokemon_height)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "$detailValue $detailUnit")
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
fun PokemonName(
    name: String,
    id: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.pokemon_id_name, id, name),
        modifier = modifier.fillMaxWidth(),
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontSize = 24.sp
    )
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
fun PokemonStat(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            statValue / statMaxValue.toFloat()
        } else 0f,
        animationSpec = tween(
            animDuration,
            animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Color(0xFF505050)
                } else {
                    Color.LightGray
                }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curPercent.value)
                .clip(CircleShape)
                .background(statColor)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = statName,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = (curPercent.value * statMaxValue).toInt().toString(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PokemonBaseStats(
    pokemonInfo: Pokemon,
    animDelayPerItem: Int = 100
) {
    val maxBaseStat = remember {
        pokemonInfo.stats.maxOf { it.baseStat }
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Base stats:",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))

        for (i in pokemonInfo.stats.indices) {
            val stat = pokemonInfo.stats[i]
            PokemonStat(
                statName = parseStatToAbbr(stat),
                statValue = stat.baseStat,
                statMaxValue = maxBaseStat,
                statColor = parseStatToColor(stat),
                animDelay = i * animDelayPerItem
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
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
