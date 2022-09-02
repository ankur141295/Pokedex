package com.ankur_anand.pokedex.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.ankur_anand.pokedex.data.remote.response.Pokemon.Stat
import com.ankur_anand.pokedex.data.remote.response.Pokemon.Type
import com.squareup.moshi.Moshi

@ProvidedTypeConverter
class ObjectTypeConverter(
    private val moshi: Moshi
) {

    @TypeConverter
    fun listOfStatToString(statList: List<Stat>): String {
        if (statList.isEmpty()) {
            return ""
        }

        val mappedList: List<String> = statList.map { stat ->
            convertObjectToJson(stat, moshi)
        }

        return convertObjectToJson(mappedList, moshi)
    }

    @TypeConverter
    fun stringToListOfStat(item: String): List<Stat> {
        if (item.isBlank()) {
            return emptyList()
        }

        val listOfString: List<String> = convertJsonToObject(item, moshi) ?: emptyList()

        if (listOfString.isEmpty()) {
            return emptyList()
        }

        val mappedList: List<Stat> = listOfString.map { stat ->
            val statObject: Stat? = convertJsonToObject(stat, moshi)
            statObject ?: return emptyList()
        }

        return mappedList
    }

    @TypeConverter
    fun listOfTypeToString(typeList: List<Type>): String {
        if (typeList.isEmpty()) {
            return ""
        }

        val mappedList: List<String> = typeList.map { type ->
            convertObjectToJson(type, moshi)
        }

        return convertObjectToJson(mappedList, moshi)
    }

    @TypeConverter
    fun stringToListOfType(item: String): List<Type> {
        if (item.isBlank()) {
            return emptyList()
        }

        val listOfString: List<String> = convertJsonToObject(item, moshi) ?: emptyList()

        if (listOfString.isEmpty()) {
            return emptyList()
        }

        val mappedList: List<Type> = listOfString.map { stat ->
            val typeObject: Type? = convertJsonToObject(stat, moshi)
            typeObject ?: return emptyList()
        }

        return mappedList
    }

}