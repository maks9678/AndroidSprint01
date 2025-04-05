package com.example.androidsprint01.data

import android.util.Log
import com.example.androidsprint01.model.Category
import com.example.androidsprint01.model.Recipe
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import java.util.concurrent.Executors

const val BASE_URL = "https://recipes.androidsprint.ru/api/"

class RecipeRepository() {

    val contentType = "application/json".toMediaType()
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
    val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    suspend fun getCategories(): List<Category>? {
        return  withContext(Dispatchers.IO) {
                val categoriesCall = service.getCategories()
                val categoriesResponse = categoriesCall.execute()
                val categories: List<Category>? = categoriesResponse.body()

        }
    }

    suspend fun getFavoritesByIdRecipes(setIdRecipe: Set<Int>): List<Recipe> {
        withContext(Dispatchers.IO) {
            try {
                val idsString = setIdRecipe.joinToString(separator = ",") { it.toString() }
                Log.e("!!!", idsString)
                val recipesCall = service.getRecipesByIds(idsString)
                val recipesResponse = recipesCall.execute()
                val recipes = recipesResponse.body()
                val listRecipe = recipes ?: emptyList<Recipe>()
                return@withContext listRecipe
            } catch (e: Exception) {
                Log.e("!!!", "Проблема с получением рецептов по id категорий: $e")
                return@withContext null
            }
        }
    }

    suspend fun getRecipesByIds(idRecipes: Int): List<Recipe> {
        withContext(Dispatchers.IO) {
            try {
                val recipeCall = service.getRecipesByCategoryId(idRecipes)
                val recipeResponse = recipeCall.execute()
                val recipes: List<Recipe> = recipeResponse.body() ?: emptyList()
                return@withContext recipes
            } catch (e: Exception) {
                Log.e("!!!", "Проблема с получением рецептов по id категорий: $e")
                return@withContext emptyList()
            }
        }
    }

    suspend fun getRecipeById(idRecipe: Int): Recipe {
        withContext(context = Dispatchers.IO) {
            try {
                val recipeCall = service.getRecipeById(idRecipe)
                val recipeResponse = recipeCall.execute()
                val recipes = recipeResponse.body()
                return@withContext recipes
            } catch (e: Exception) {
                Log.e("!!!", "Проблема с получением рецептов по id категорий: $e")
                return@withContext null
            }
        }
    }
}