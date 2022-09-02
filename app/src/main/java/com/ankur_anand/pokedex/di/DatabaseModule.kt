package com.ankur_anand.pokedex.di

import android.content.Context
import androidx.room.Room
import com.ankur_anand.pokedex.data.local.PokemonDatabase
import com.ankur_anand.pokedex.utils.Constants.POKEMON_DATABASE
import com.ankur_anand.pokedex.utils.ObjectTypeConverter
import com.squareup.moshi.Moshi
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
        @ApplicationContext context: Context,
        moshi: Moshi
    ): PokemonDatabase {
        val typeConverter = ObjectTypeConverter(moshi = moshi)

        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            POKEMON_DATABASE
        ).addTypeConverter(typeConverter).build()
    }

}