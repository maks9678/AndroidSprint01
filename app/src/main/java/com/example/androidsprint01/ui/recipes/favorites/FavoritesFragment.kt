package com.example.androidsprint01.ui.recipes.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.androidsprint01.R
import com.example.androidsprint01.data.BackendSingleton
import com.example.androidsprint01.databinding.FragmentFavoritesBinding
import com.example.androidsprint01.ui.categories.CategoriesListFragment.Companion.ARG_LIST_RECIPE
import com.example.androidsprint01.ui.recipes.recipe.RecipeFragment
import com.example.androidsprint01.ui.recipes.recipesList.RecipesListAdapter
import com.example.androidsprint01.ui.recipes.recipesList.RecipesListFragment

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")
    private var favoritesAdapter: RecipesListAdapter = RecipesListAdapter(emptyList())
    private val viewModel: FavoritesViewModel by viewModels()

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
        viewModel.loadFavorites()
        setupRecycler(view)
    }

    fun setupRecycler(view: View) {
        binding.rvFavorites.adapter = favoritesAdapter
        viewModel.favoritesState.observe(viewLifecycleOwner,Observer{ favoritesState ->
            val favoritesRecipe = viewModel.favoritesState.value?.favoritesList
            Log.d("!!!","$favoritesRecipe")
            if (favoritesRecipe.isNullOrEmpty()) {
                binding.tv0Favorites.visibility = View.VISIBLE
            } else {
                binding.tv0Favorites.visibility = View.INVISIBLE
                favoritesAdapter =
                    RecipesListAdapter(favoritesRecipe).apply {
                        setOnItemClickListener(object : RecipesListAdapter.OnRecipeClickListener {
                            override fun onRecipeItemClick(recipeId: Int) {
                                openRecipeByRecipeId(recipeId,view)
                            }
                        })
                    }
            }
        })
    }

    private fun openRecipeByRecipeId(recipeId: Int,view: View) {
        val recipe = BackendSingleton.getRecipeById(recipeId)
        val bundle =
            Bundle().apply { putParcelable(RecipesListFragment.Companion.ARG_RECIPE, recipe) }

        val button = view.findViewById<Button>(R.id.recipeFragment,)
        button.setOnClickListener {
            findNavController().navigate(R.id.favoritesFragment,bundle)
        }
    }
}
