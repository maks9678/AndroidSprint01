package com.example.androidsprint01.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.NavHostFragment
import com.example.androidsprint01.ui.recipes.favorites.FavoritesFragment
import com.example.androidsprint01.R
import com.example.androidsprint01.databinding.ActivityMainBinding
import com.example.androidsprint01.ui.categories.CategoriesListFragment

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    val navHostFragment =
        supportFragmentManager.findFragmentById(R.id.categoriesListFragment) as NavHostFragment
    val navController = navHostFragment.navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<CategoriesListFragment>(R.id.main_container)

            }
        }
        binding.buttonFavorites.setOnClickListener {
            supportFragmentManager.commit {
                replace<FavoritesFragment>(R.id.main_container)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }
        binding.buttonCategories.setOnClickListener {
            supportFragmentManager.commit {
                replace<CategoriesListFragment>(R.id.main_container)
                setReorderingAllowed(true)
                addToBackStack(null)

            }
        }
    }
}