package com.example.androidsprint01.data

import android.util.Log
import com.example.androidsprint01.model.Category
import com.example.androidsprint01.model.Recipe
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import java.util.concurrent.Executors

const val BASE_URL = "https://recipes.androidsprint.ru/api/"

class RecipeRepository() {
    val threadPool = Executors.newFixedThreadPool(10)

    val contentType = "application/json".toMediaType()
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
    val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    fun getCategories(callback: (List<Category>?) -> Unit) {
        threadPool.submit {
            try {
                val categoriesCall = service.getCategories()
                val categoriesResponse = categoriesCall.execute()

                val categories: List<Category>? = categoriesResponse.body()
                callback(categories)

            } catch (e: Exception) {
                Log.e("!!!", "Проблема с получением категорий: $e")
                callback(null)
            }
        }
    }

    fun getFavoritesByIdRecipes(setIdRecipe: Set<Int>, callback: (List<Recipe>?) -> Unit) {
        threadPool.submit {
            try {
                val idsString = setIdRecipe.joinToString(separator = ",") { it.toString() }
                Log.e("!!!",idsString)
                val recipesCall = service.getRecipesByIds(idsString)
                val recipesResponse = recipesCall.execute()
                val recipes = recipesResponse.body()
                val listRecipe = recipes ?: emptyList<Recipe>()
                callback(listRecipe)
            } catch (e: Exception) {
                Log.e("!!!", "Проблема с получением рецептов по id категорий: $e")
                callback(null)
            }
        }
    }

    fun getRecipesByIds(idRecipes: Int, callback: (List<Recipe>) -> Unit) {
        threadPool.submit {

            try {
                val recipeCall = service.getRecipesByCategoryId(idRecipes)
                val recipeResponse = recipeCall.execute()
                val recipes: List<Recipe> = recipeResponse.body() ?: emptyList()
                callback(recipes)
            } catch (e: Exception) {
                Log.e("!!!", "Проблема с получением рецептов по id категорий: $e")
                callback(emptyList())
            }
        }
    }

    fun getRecipeById(idRecipe: Int, callback: (Recipe?) -> Unit) {
        threadPool.submit {
            try {
                val recipeCall = service.getRecipeById(idRecipe)
                val recipeResponse = recipeCall.execute()
                val recipes = recipeResponse.body()
                callback(recipes)
            } catch (e: Exception) {
                Log.e("!!!", "Проблема с получением рецептов по id категорий: $e")
                callback(null)
            }
        }
    }
}