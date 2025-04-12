package com.example.androidsprint01.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidsprint01.model.Category
import com.example.androidsprint01.model.Recipe

@TypeConverters(Converters::class)
@Database(entities = [Category::class, Recipe::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoriesDao
    abstract fun recipesDao(): RecipesDao

    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getCategoriesDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext, // Используем applicationContext
                    AppDatabase::class.java,
                    "database-categories"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = database
                database
            }
        }

        @Volatile
        var InstantRecipe: AppDatabase? = null
        fun getRecipesDatabase(context: Context): AppDatabase {
            return InstantRecipe ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database-recipe"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                InstantRecipe = database
                database
            }
        }
    }
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

}
