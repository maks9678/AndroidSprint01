package com.example.androidsprint01.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.androidsprint01.R
import com.example.androidsprint01.databinding.ActivityMainBinding
import java.net.HttpURLConnection
import java.net.URL
import android.util.Log
import com.example.androidsprint01.model.Category
import com.example.androidsprint01.model.Recipe
import kotlinx.serialization.json.Json
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFavorites.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.favoritesFragment)
        }
        binding.buttonCategories.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.categoriesListFragment)
        }
        val url = URL("https://recipes.androidsprint.ru/api/category")
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val json = Json { ignoreUnknownKeys = true }
        var listCategories = emptyList<Category>()
        val thread = Thread {
            try {
                connection.connect()

                Log.i("!!!", "Выполняю запрос на потоке:${Thread.currentThread().name}")
                Log.i("!!!", "responseCode:${connection.responseCode}")
                Log.i("!!!", "responseMessage:${connection.responseMessage}")

                val responseBody = connection.inputStream.bufferedReader().readText()
                Log.i("!!!", "Body:${responseBody}")


                listCategories = json.decodeFromString(responseBody)

                Log.i("!!!", "$listCategories")
            } catch (e: Exception) {
                Log.i("!!!", "$e")
            } finally {
                connection.disconnect()
            }
        }
        thread.start()
        Log.i("!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")
        thread.join()
        fetchRecipes(listCategories)

    }

    fun fetchRecipes(listCategories: List<Category>) {

        val threadPool = Executors.newFixedThreadPool(10)
        val idCategories = listCategories.map { it.id }
        val listRecipes = MutableList(idCategories.size) { emptyList<Recipe>() }
        val json = Json { ignoreUnknownKeys = true }
        Log.i("!!!", "Количество категорий: ${idCategories.size}")
        idCategories.forEachIndexed { index,idCategory ->
            val urlRecipes =
                URL("https://recipes.androidsprint.ru/api/category/$idCategory/recipes")
            val connectionRecipes = urlRecipes.openConnection() as HttpURLConnection
            threadPool.submit {
                try {
                    connectionRecipes.connect()
                    val responseBodyRecipes =
                        connectionRecipes.inputStream.bufferedReader().readText()
                    val listRecipe: List<Recipe> =
                        json.decodeFromString(responseBodyRecipes)
                    listRecipes[index] = listRecipe
                    Log.i("!!!", "Получены рецепты для категории $index: $listRecipe")
                } catch (e: Exception) {
                    Log.i("!!!", "Проблема с получением рецептов по id категорий: $e")
                } finally {
                    connectionRecipes.disconnect()
                }
            }
        }
        threadPool.shutdown()
        while (!threadPool.isTerminated) { }
        Log.i("!!!", "Финальный список рецептов: $listRecipes")
    }
}
