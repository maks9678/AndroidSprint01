package com.example.androidsprint01

data class Recipes(
    val id: Int,
    val title: String,
    val ingredients: Ingredient,
    val method: List<String>,
    val imageUrl: String
)

data class Categories(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
)

data class Ingredient(
    val quantity: Float,
    val unitOfMeasure: String,
    val description: String
)