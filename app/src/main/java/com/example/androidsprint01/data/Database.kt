package com.example.androidsprint01.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidsprint01.model.Category


@Database(entities = [Category::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoriesDao

    companion object{
        @Volatile
        var INSTANCE : AppDatabase?  =null

        fun getCategoriesDatabase(context: Context): AppDatabase {
            return INSTANCE?: synchronized(this) {
            val categoriesDao = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "database-categories"
            ).fallbackToDestructiveMigration().build()
            INSTANCE=categoriesDao
            categoriesDao}
        }
}}

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(categories: List<Category>)
}
