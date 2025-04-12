package com.example.androidsprint01.data

import android.app.Application
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

class RecipeRepository(
    application: Application,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    val databaseRecipe = AppDatabase.getRecipesDatabase(application)
    val databaseCategories = AppDatabase.getCategoriesDatabase(application)
    val categoriesDao = databaseCategories.categoryDao()
    val recipesDao = databaseRecipe.recipesDao()

    val contentType = "application/json".toMediaType()
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
    val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    suspend fun getCategoriesFromCache(): List<Category> {

        Log.i("RecipeRepository", "${categoriesDao.getAllCategories()}")
        return categoriesDao.getAllCategories()
    }

    suspend fun getRecipesFromCache(): List<Recipe> {
        Log.i("RecipeRepository", "${recipesDao.getAllRecipes()}")
        return recipesDao.getAllRecipes()
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

    suspend fun getRecipesByIds(idCategory: Int): List<Recipe> {
        return withContext(dispatcher) {
            try {
                val recipeResponse = service.getRecipesByCategoryId(idCategory)
                val recipesWithCategory = recipeResponse.map { recipe ->
                    recipe.copy( idCategory)
                }
                recipesWithCategory
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