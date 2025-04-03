package com.example.androidsprint01.data

import com.example.androidsprint01.model.Category
import com.example.androidsprint01.model.Recipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {

    @GET("category")
    fun getCategories(): Call<List<Category>>

    @GET("category/{idListRecipes}/recipes")
    fun getRecipesByCategoryId(@Path("idListRecipes") idRecipes: Int): Call<List<Recipe>>

    @GET("recipe/{idRecipe}")
    fun getRecipeById(@Path("idRecipe") idRecipes: Int): Call<Recipe>

    @GET("recipes") // Change the path to "recipes"
    fun getRecipesByIds(@Query("ids") listRecipes: String): Call<List<Recipe>> // Use @Query
}