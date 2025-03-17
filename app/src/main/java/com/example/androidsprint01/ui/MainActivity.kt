package com.example.androidsprint01.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.androidsprint01.ui.recipes.favorites.FavoritesFragment
import com.example.androidsprint01.R
import com.example.androidsprint01.databinding.ActivityMainBinding
import com.example.androidsprint01.ui.categories.CategoriesListFragment

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFavorites.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.categoriesListFragment)
        }
        binding.buttonCategories.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.favoritesFragment)
    }
}}