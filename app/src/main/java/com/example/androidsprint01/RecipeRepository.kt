package com.example.androidsprint01

import com.example.androidsprint01.data.BackendSingleton.burgerRecipes
import com.example.androidsprint01.model.Category
import com.example.androidsprint01.model.Recipe
import com.example.androidsprint01.ui.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.Executors

class RecipeRepository (){
    val threadPool = Executors.newFixedThreadPool(10)

    val contentType = "application/json".toMediaType()
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
    val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    fun getCategories(): List<Category>{

        val categoriesCall = service.getCategories()
        val categoriesResponse = categoriesCall.execute()
        val categories: List<Category> = categoriesResponse.body() ?: emptyList()
        return categories
    }

    fun getRecipesByIds (setIdRecipe: Set<Recipe>): List<Recipe>{
        val recipesCall = service.getRecipeById(setIdRecipe)
        val recipesResponse = recipesCall.execute()
        val recipes = recipesResponse.body()
        val listRecipe = recipes ?:emptyList<Recipe>()
        return listRecipe
    }

    fun getRecipeById( idRecipes: Int):List<Recipe>{

            val recipeCall = service.getRecipesByCategoryId(idRecipes)
            val recipeResponse = recipeCall.execute()
            val recipes: List<Recipe> = recipeResponse.body() ?: emptyList()
            return recipes

        }
    fun getRecipeById( idRecipe: Int):Recipe{

            val recipeCall = service.getRecipesByCategoryId(idRecipe)
            val recipeResponse = recipeCall.execute()
            val recipes: Recipe = recipeResponse.body() ?:
            return recipes

        }


}