package com.ankur_anand.pokedex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ankur_anand.pokedex.data.model.PokedexListRemoteKeys

@Dao
interface PokedexListRemoteKeysDao {

    @Query("SELECT * FROM pokemon_list_remote_key_table WHERE id =:id")
    suspend fun getRemoteKeys(id: String): PokedexListRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<PokedexListRemoteKeys>)

    @Query("DELETE FROM pokemon_list_remote_key_table")
    suspend fun deleteAllRemoteKeys()

}