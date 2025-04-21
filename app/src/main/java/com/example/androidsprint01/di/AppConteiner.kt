package com.example.androidsprint01.di

import android.content.Context
import com.example.androidsprint01.data.AppDatabase
import com.example.androidsprint01.data.BASE_URL
import com.example.androidsprint01.data.RecipeApiService
import com.example.androidsprint01.data.RecipeRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class AppConteiner(context: Context) {
    val database = AppDatabase.getDatabase(context)
    val contentType = "application/json".toMediaType()
    val logging = HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()

    val recipeApiService: RecipeApiService = retrofit.create(RecipeApiService::class.java)
    val repository = RecipeRepository(
        recipeApiService,
        database.categoryDao(),
        database.recipesDao(),
        Dispatchers.IO
    )

    val categoriesListViewModelFactory = CategoriesListViewModelFactory(repository)
    val recipesListViewModelFactory = RecipesListViewModelFactory(repository)
    val favoritesViewModelFactory = FavoritesViewModelFactory(repository)
    val recipeViewModelFactory = RecipeViewModelFactory(repository)
}