package com.example.androidsprint01.data

import com.example.androidsprint01.model.Category
import com.example.androidsprint01.model.Recipe
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {

    @GET("category")
    suspend fun getCategories(): List<Category>

    @GET("category/{idListRecipes}/recipes")
    suspend fun getRecipesByCategoryId(@Path("idListRecipes") idRecipes: Int): List<Recipe>

    @GET("recipe/{idRecipe}")
    suspend fun getRecipeById(@Path("idRecipe") idRecipes: Int): Recipe

    @GET("recipes")
    suspend fun getRecipesByIds(@Query("ids") listRecipes: String): List<Recipe>
}