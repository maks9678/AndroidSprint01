package com.example.androidsprint01


import com.example.androidsprint01.model.Category
import com.example.androidsprint01.model.Recipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface RecipeApiService {

    @GET("category")
    fun getCategories(): Call<List<Category>>

    @GET("category/{idRecipes}/recipes")
    fun getRecipesByCategoryId(@Path("idRecipes") idRecipes: Int): Call<List<Recipe>>

    @GET("recipe/{idRecipes}")
    fun getRecipeById(@Path("idRecipes") idRecipes: Int): Call<Recipe>

    @GET("category/recipes")
    fun getRecipesByIds(@Path("listRecipes") listRecipes: Set<Recipe>): Call<List<Recipe>>


}