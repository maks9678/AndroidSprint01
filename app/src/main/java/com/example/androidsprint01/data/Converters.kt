package com.example.androidsprint01.data

import androidx.room.TypeConverter
import com.example.androidsprint01.model.Ingredient
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun fromIngredientList(value: List<Ingredient>): String = Json.encodeToString(value)

    @TypeConverter
    fun toIngredientList(value: String) = Json.decodeFromString<List<Ingredient>>(value)

    @TypeConverter
    fun fromStringList(value: List<String>): String = Json.encodeToString(value)

    @TypeConverter
    fun toStringList(value: String) = Json.decodeFromString<List<String>>(value)
}