package com.example.androidsprint01.model

import android.os.Parcelable
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
) : Parcelable

@Parcelize
@Serializable
data class Category(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
): Parcelable

@Serializable
@Parcelize
data class Ingredient(
    val quantity: String,
    val unitOfMeasure: String,
    val description: String
) : Parcelable





