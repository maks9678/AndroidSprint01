package com.example.androidsprint01.di

import android.content.Context
import androidx.room.Room
import com.example.androidsprint01.data.AppDatabase
import com.example.androidsprint01.data.BASE_URL
import com.example.androidsprint01.data.CategoriesDao
import com.example.androidsprint01.data.RecipeApiService
import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.data.RecipesDao
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RecipeModule() {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context, AppDatabase::class.java,
            "database"
        ).build()

    @Provides
    fun provideCategoriesDao(appDatabase: AppDatabase): CategoriesDao = appDatabase.categoryDao()

    @Provides
    fun provideRecipeDao(appDatabase: AppDatabase): RecipesDao = appDatabase.recipesDao()

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
        return retrofit
    }
    @Provides
    fun provideRecipeRepository(
        categoriesDao: CategoriesDao,
        recipesDao: RecipesDao,
        recipeApiService: RecipeApiService
    ): RecipeRepository = RecipeRepository(
        recipeApiService,categoriesDao,recipesDao)


    @Provides
    fun provideRecipeApiService(retrofit: Retrofit): RecipeApiService {
        return retrofit.create(RecipeApiService::class.java)
    }
}