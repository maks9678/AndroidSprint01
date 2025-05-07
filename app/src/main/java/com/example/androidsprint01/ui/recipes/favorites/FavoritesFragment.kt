package com.example.androidsprint01.ui.recipes.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.androidsprint01.R
import com.example.androidsprint01.di.RecipeApplication
import com.example.androidsprint01.databinding.FragmentFavoritesBinding
import com.example.androidsprint01.ui.recipes.recipesList.RecipesListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment(
    private var favoritesAdapter: RecipesListAdapter = RecipesListAdapter(emptyList())
) :
    Fragment(R.layout.fragment_favorites) {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")

    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavorites()
    }

    private fun setupRecycler() {
        viewModel.favoritesState.observe(viewLifecycleOwner, Observer { favoritesState ->
            val favoritesRecipe = favoritesState.favoritesList
            Log.d("FavoritesFragment", "$favoritesRecipe")
            if (favoritesRecipe.isNullOrEmpty()) {
                binding.tv0Favorites.visibility = View.VISIBLE
            } else {
                binding.tv0Favorites.visibility = View.INVISIBLE
                favoritesAdapter =
                    RecipesListAdapter(favoritesRecipe).apply {
                        setOnItemClickListener(object : RecipesListAdapter.OnRecipeClickListener {
                            override fun onRecipeItemClick(recipeId: Int) {
                                openRecipeByRecipeId(recipeId)
                            }
                        })
                    }
                binding.rvFavorites.adapter = favoritesAdapter
            }
        })
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val action =
            FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment(recipeId)
        findNavController().navigate(action)
    }
}
