package com.ankur_anand.pokedex.di

import com.ankur_anand.pokedex.data.local.PokemonDatabase
import com.ankur_anand.pokedex.data.remote.ApiService
import com.ankur_anand.pokedex.data.repository.PokeRepository
import com.ankur_anand.pokedex.data.repository.PokeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun providePokemonRepository(
        apiService: ApiService,
        pokemonDatabase: PokemonDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): PokeRepository {
        return PokeRepositoryImpl(
            apiService = apiService,
            pokemonDatabase = pokemonDatabase,
            ioDispatcher = ioDispatcher
        )
    }
}