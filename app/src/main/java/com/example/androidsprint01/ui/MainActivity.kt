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
import kotlinx.serialization.json.Json

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
        val thread = Thread {
            try {
                connection.connect()

                Log.i("!!!", "Выполняю запрос на потоке:${Thread.currentThread().name}")
                Log.i("!!!", "responseCode:${connection.responseCode}")
                Log.i("!!!", "responseMessage:${connection.responseMessage}")

                val responseBody = connection.inputStream.bufferedReader().readText()
                Log.i("!!!", "Body:${responseBody}")

                val json = Json { ignoreUnknownKeys = true }
                val listCategories : List<Category> = json.decodeFromString(responseBody)
                Log.i("!!!", "$listCategories")
            } catch (e: Exception) {
                Log.i("!!!", "$e")
            } finally {
                connection.disconnect()
            }
        }
        thread.start()
        Log.i("!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

    }
}