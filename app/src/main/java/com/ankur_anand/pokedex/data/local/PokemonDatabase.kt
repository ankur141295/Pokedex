package com.ankur_anand.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ankur_anand.pokedex.data.local.dao.PokedexListEntryDao
import com.ankur_anand.pokedex.data.local.dao.PokedexListRemoteKeysDao
import com.ankur_anand.pokedex.data.local.dao.PokemonDao
import com.ankur_anand.pokedex.data.model.PokedexListEntry
import com.ankur_anand.pokedex.data.model.PokedexListRemoteKeys
import com.ankur_anand.pokedex.data.remote.response.Pokemon
import com.ankur_anand.pokedex.utils.ObjectTypeConverter

@Database(
    entities = [PokedexListEntry::class, PokedexListRemoteKeys::class/*, Pokemon::class*/],
    version = 1
)
/*@TypeConverters(ObjectTypeConverter::class)*/
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokedexLisEntrytDao(): PokedexListEntryDao
    abstract fun pokedexListRemoteKeysDao(): PokedexListRemoteKeysDao
 /*   abstract fun pokemonDao(): PokemonDao*/

}