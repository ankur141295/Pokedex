package com.ankur_anand.pokedex.di

import android.content.Context
import androidx.room.Room
import com.ankur_anand.pokedex.data.local.PokemonDatabase
import com.ankur_anand.pokedex.utils.Constants.POKEMON_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            POKEMON_DATABASE
        ).build()
    }

}