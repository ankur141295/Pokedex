package com.ankur_anand.pokedex.di

import com.ankur_anand.pokedex.data.local.PokemonDatabase
import com.ankur_anand.pokedex.data.remote.ApiService
import com.ankur_anand.pokedex.data.repository.PokeRepository
import com.ankur_anand.pokedex.data.repository.PokeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(
        apiService: ApiService,
        pokemonDatabase: PokemonDatabase
    ): PokeRepository {
        return PokeRepositoryImpl(apiService = apiService, pokemonDatabase = pokemonDatabase)
    }
}