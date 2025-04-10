package com.example.androidsprint01.data

import android.util.Log
import com.example.androidsprint01.model.Category
import com.example.androidsprint01.model.Recipe
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

const val BASE_URL = "https://recipes.androidsprint.ru/api/"

class RecipeRepository(val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    val contentType = "application/json".toMediaType()
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
    val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    suspend fun getCategoriesFromCache(database: AppDatabase): List<Category> {
        Log.i("RecipeRepository", "${database.categoryDao().getAllCategories()}")
        return database.categoryDao().getAllCategories()
    }

    suspend fun getCategories(): List<Category>? {
        return withContext(dispatcher) {
            try {
                val categoriesResponse = service.getCategories()
                Log.i("RecipeRepository", "${categoriesResponse}")
                categoriesResponse

            } catch (e: Exception) {
                Log.e("RecipeRepository", "Проблема с получением категорий: $e")
                emptyList()
            }
        }
    }

    suspend fun getFavoritesByIdRecipes(setIdRecipe: Set<Int>): List<Recipe> {
        return withContext(dispatcher) {
            try {
                val idsString = setIdRecipe.joinToString(separator = ",") { it.toString() }
                Log.e("RecipeRepository", idsString)
                val recipesResponse = service.getRecipesByIds(idsString)
                recipesResponse
            } catch (e: Exception) {
                Log.e("RecipeRepository", "Проблема с получением рецептов по id категорий: $e")
                emptyList()
            }
        }
    }

    suspend fun getRecipesByIds(idRecipes: Int): List<Recipe> {
        return withContext(dispatcher) {
            try {
                val recipeResponse = service.getRecipesByCategoryId(idRecipes)
                recipeResponse
            } catch (e: Exception) {
                Log.e("RecipeRepository", "Проблема с получением рецептов по id категорий: $e")
                emptyList()
            }
        }
    }

    suspend fun getRecipeById(idRecipe: Int): Recipe? {
        return withContext(context = dispatcher) {
            try {
                val recipeResponse = service.getRecipeById(idRecipe)
                recipeResponse
            } catch (e: Exception) {
                Log.e("RecipeRepository", "Проблема с получением рецептов по id категорий: $e")
                null
            }
        }
    }
}