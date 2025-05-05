package com.example.androidsprint01.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidsprint01.model.Category
import com.example.androidsprint01.model.Recipe

@TypeConverters(Converters::class)
@Database(entities = [Category::class, Recipe::class], version = 21, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoriesDao
    abstract fun recipesDao(): RecipesDao
}

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(categories: List<Category>)
}

@Dao
interface RecipesDao {

    @Query("SELECT * FROM recipe")
    suspend fun getAllRecipes(): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipes(recipes: List<Recipe>)

    @Query("SELECT * FROM recipe WHERE id BETWEEN :startId AND :endId")
    suspend fun getRecipesByCategoryIds(startId: Int, endId: Int): List<Recipe>

    @Query("SELECT * FROM recipe WHERE isFavorite = 1")
    suspend fun getFavoriteRecipes(): List<Recipe>

    @Query("SELECT * FROM recipe WHERE categoryId = :categoryId")
    suspend fun getRecipesByCategoryId(categoryId:Int): List<Recipe>
}