package com.example.androidsprint01.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.androidsprint01.R
import com.example.androidsprint01.databinding.ActivityMainBinding
import com.example.androidsprint01.model.Category
import com.example.androidsprint01.model.Recipe
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import okio.use
import java.net.URL
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
        val json = Json { ignoreUnknownKeys = true }
        var listCategories = emptyList<Category>()
        val thread = Thread {
            try {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY // Уровень логирования
                }
                val client = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
                val request = Request.Builder()
                    .url(url)
                    .build()
                client.newCall(request).execute().use { response ->
                    Log.i("!!!", "Выполняю запрос на потоке:${Thread.currentThread().name}")
                    Log.i("!!!", "responseMessage:${response.message}")
                        val responseBody = response.body?.string()
                        Log.i("!!!", "Body:$responseBody")
                            listCategories = json.decodeFromString(responseBody ?:"")
                            Log.i("!!!", "$listCategories")
                    }
            } catch (e: Exception) {
                Log.i("!!!", "$e")
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
        idCategories.forEachIndexed { index, idCategory ->

            val client = OkHttpClient()
            val urlRecipes =
                URL("https://recipes.androidsprint.ru/api/category/$idCategory/recipes")
            val request = Request.Builder()
                .url(urlRecipes)
                .build()
            threadPool.submit {
                try {
                    val response = client.newCall(request).execute()
                    if (response.isSuccessful) {
                        val responseBodyRecipes = response.body?.string()
                        if (responseBodyRecipes != null) {
                            val listRecipe: List<Recipe> =
                                json.decodeFromString(responseBodyRecipes)
                            Log.i("!!!", "Получены рецепты для категории $index: $listRecipe")
                            listRecipes[index] = listRecipe
                        }
                    } else {
                        Log.i("!!!", "Ошибка :${response.code} – ${response.message}")
                    }
                } catch (e: Exception) {
                    Log.i("!!!", "Проблема с получением рецептов по id категорий: $e")
                }
            }
        }
        threadPool.shutdown()
        while (!threadPool.isTerminated) {
        }
        Log.i("!!!", "Финальный список рецептов: $listRecipes")
    }
}