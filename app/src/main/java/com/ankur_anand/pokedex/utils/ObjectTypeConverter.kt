package com.ankur_anand.pokedex.utils

import androidx.room.TypeConverter
import com.ankur_anand.pokedex.data.remote.response.Pokemon.Stat
import com.ankur_anand.pokedex.data.remote.response.Pokemon.Stat.StatX
import com.ankur_anand.pokedex.data.remote.response.Pokemon.Type
import com.ankur_anand.pokedex.data.remote.response.Pokemon.Type.TypeX
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ObjectTypeConverter {

    private val moshi: Moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    @TypeConverter
    fun listOfStatToString(stats: List<Stat>): String =
        convertObjectToJson(stats, moshi)

    @TypeConverter
    fun stringToListOfStat(item: String): List<Stat> =
        convertJsonToObject(item, moshi) ?: emptyList()

    @TypeConverter
    fun statXToString(statX: StatX): String = convertObjectToJson(statX, moshi)

    @TypeConverter
    fun stringToStatX(item: String): StatX =
        convertJsonToObject(item, moshi) ?: StatX(name = "", url = "")

    @TypeConverter
    fun listOfTypeToString(types: List<Type>): String =
        convertObjectToJson(types, moshi)

    @TypeConverter
    fun stringToListOfType(item: String): List<Type> =
        convertJsonToObject(item, moshi) ?: emptyList()

    @TypeConverter
    fun typeXToString(typeX: TypeX): String = convertObjectToJson(typeX, moshi)

    @TypeConverter
    fun stringToTypeX(item: String): TypeX =
        convertJsonToObject(item, moshi) ?: TypeX(name = "", url = "")
}