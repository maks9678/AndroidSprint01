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
    val database = AppDatabase.getDatabase(application)
    val categoriesDao = database.categoryDao()
    val recipesDao = database.recipesDao()

    val contentType = "application/json".toMediaType()
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
    val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    suspend fun getCategoriesFromCache(): List<Category> {

        Log.i("RecipeRepository", "getCategoriesFromCache: ${categoriesDao.getAllCategories()}")
        return categoriesDao.getAllCategories()
    }

    suspend fun getRecipesFromCacheById(idCategory: Int): List<Recipe> {
        Log.i("RecipeRepository", "${recipesDao.getAllRecipes()}")
        return recipesDao.getRecipesByCategoryId(
            idCategory * 100,
            idCategory * 100 + 99
        )
    }

    suspend fun getCategories(): List<Category>? {
        return withContext(dispatcher) {
            try {
                val categoriesResponse = service.getCategories()
                Log.i("RecipeRepository", "getCategories :${categoriesResponse}")
                categoriesResponse

            } catch (e: Exception) {
                Log.e("RecipeRepository", "Проблема с получением категорий: $e")
                emptyList()
            }
        }
    }

    suspend fun getRecipesByIds(idCategory: Int): List<Recipe> {
        return withContext(dispatcher) {
            try {

                val recipeResponse = service.getRecipesByCategoryId(idCategory)
                val recipe = recipeResponse.mapIndexed { index, recipe ->
                    val recipeFromCache = recipesDao.getAllRecipes().find { it.id == idCategory * 100 + index }
                    val isFavorite = recipeFromCache?.isFavorite ?: false
                    recipe.copy(id = idCategory * 100 + index, isFavorite = isFavorite)}
                recipesDao.addRecipes(recipe)
                recipe
            } catch (e: Exception) {
                Log.e("RecipeRepository", "Проблема с получением рецептов по id категорий: $e")
                emptyList()
            }
        }
    }

    suspend fun getRecipeById(idRecipe: Int): Recipe? {
        return withContext(context = dispatcher) {
            try {
                val recipeFromCache = recipesDao.getAllRecipes().find { it.id == idRecipe }
                Log.i("RecipeRepository", "recipeFromCache :${recipeFromCache?.isFavorite}")
                if (recipeFromCache != null) {
                    recipeFromCache
                } else {
                    val recipeResponse = service.getRecipeById(idRecipe)
                    val isFavorite =
                        recipesDao.getAllRecipes().find { it.id == recipeResponse.id }?.isFavorite
                            ?: false
                    val recipe =
                        recipeResponse.copy(isFavorite = isFavorite)
                    Log.i("RecipeRepository", "recipe :${recipe.isFavorite}")
                    recipe
                }
            } catch (e: Exception) {
                Log.e("RecipeRepository", "Проблема с получением рецептa по id категорий: $e")
                null
            }
        }
    }

    suspend fun updateFavoriteStatus(recipeId: Int, isFavorite: Boolean) {
        recipesDao.updateFavoriteStatus(recipeId, isFavorite)
    }

    suspend fun getFavoriteRecipes(): List<Recipe> {
        return withContext(dispatcher) {
            try {
                val currentListFavorite = recipesDao.getFavoriteRecipes()
                currentListFavorite
            } catch (e: Exception) {
                Log.e("RecipeRepository", "Проблема с получением избранных рецептов: $e")
                emptyList()
            }
        }
    }
}