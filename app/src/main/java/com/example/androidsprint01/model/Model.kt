package com.example.androidsprint01.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidsprint01.data.BASE_URL
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity(tableName = "recipe")
data class Recipe(

    @PrimaryKey val id: Int,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("ingredients")val ingredients: List<Ingredient>,
    @ColumnInfo("method")val method: List<String>,
    @ColumnInfo("imageUrl")val imageUrl: String,
    @ColumnInfo("isFavorite")var isFavorite: Boolean=false,
) : Parcelable {
    val fullImageUrl: String
        get() = "${BASE_URL}images/$imageUrl"
}

@Parcelize
@Serializable
@Entity(tableName = "category")
data class Category(
    @PrimaryKey val id: Int,
    @ColumnInfo("title")val title: String,
    @ColumnInfo("description")val description: String,
    @ColumnInfo("imageUrl")val imageUrl: String,
    @ColumnInfo("isFavorite") val isFavorite: Boolean=false
) : Parcelable {
    val fullImageUrl: String
        get() = "${BASE_URL}images/$imageUrl"
}

@Serializable
@Parcelize
@Entity(tableName = "ingredient")
data class Ingredient(
    @ColumnInfo("quantity") val quantity: String,
    @ColumnInfo(name= "unit_of_measure") val unitOfMeasure: String,
    @ColumnInfo("description")val description: String
) : Parcelable