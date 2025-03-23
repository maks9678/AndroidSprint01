package com.example.androidsprint01.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.androidsprint01.R
import com.example.androidsprint01.databinding.ActivityMainBinding
import java.net.HttpURLConnection
import java.net.URL
import android.util.Log
import com.example.androidsprint01.model.Categories
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.concurrent.thread

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
        val thread = Thread{
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            val currentThread = Thread.currentThread().name

            Log.i("!!!","Выполняю запрос на потоке:${currentThread}")
            Log.i("!!!","responseCode:${connection.responseCode}")
            Log.i("!!!","responseMessage:${connection.responseMessage}")
            val responseBody = connection.inputStream.bufferedReader().toString()
            Log.i("!!!","Body:${responseBody}")

            val categories:List<Categories> = Json.decodeFromString(responseBody)
            categories.forEach{

                Log.i("!!!","Body:${it}")
            }

        }
        thread.start()
        val currentThread1 = Thread.currentThread().name
        Log.i("!!!","Метод onCreate() выполняется на потоке:${currentThread1}")

        thread.start()

        // Логируем текущий поток
        Log.i("!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")
    }

}