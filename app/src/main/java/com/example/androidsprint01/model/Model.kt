package com.example.androidsprint01.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidsprint01.data.BASE_URL
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Recipe(
    val id: Int,
    val title: String,
    val ingredients: List<Ingredient>,
    val method: List<String>,
    val imageUrl: String
) : Parcelable {
    val fullImageUrl: String
        get() = "${BASE_URL}images/$imageUrl"
}

@Parcelize
@Serializable
@Entity
data class Category(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
) : Parcelable {
    val fullImageUrl: String
        get() = "${BASE_URL}images/$imageUrl"
}

@Serializable
@Parcelize
data class Ingredient(
    val quantity: String,
    val unitOfMeasure: String,
    val description: String
) : Parcelable





