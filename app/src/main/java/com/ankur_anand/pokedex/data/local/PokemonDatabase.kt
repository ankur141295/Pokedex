package com.ankur_anand.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ankur_anand.pokedex.data.local.dao.PokedexListEntryDao
import com.ankur_anand.pokedex.data.local.dao.PokedexListRemoteKeysDao
import com.ankur_anand.pokedex.data.model.PokedexListEntry
import com.ankur_anand.pokedex.data.model.PokedexListRemoteKeys

@Database(entities = [PokedexListEntry::class, PokedexListRemoteKeys::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokedexLisEntrytDao(): PokedexListEntryDao
    abstract fun pokedexListRemoteKeysDao(): PokedexListRemoteKeysDao

}