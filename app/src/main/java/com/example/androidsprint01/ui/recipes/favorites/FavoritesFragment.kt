package com.example.androidsprint01.ui.recipes.favorites

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.androidsprint01.R
import com.example.androidsprint01.data.BackendSingleton
import com.example.androidsprint01.databinding.FragmentFavoritesBinding
import com.example.androidsprint01.ui.recipes.recipe.RecipeFragment
import com.example.androidsprint01.ui.recipes.recipesList.RecipesListAdapter
import com.example.androidsprint01.ui.recipes.recipesList.RecipesListFragment

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")
    private var favoritesAdapter: RecipesListAdapter? = null
    private val sharedPrefs: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(
            RecipeFragment.Companion.ARG_PREFERENCES,
            Context.MODE_PRIVATE
        )
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

    private fun getFavorites(): Set<Int> {
        val stringFavorites = sharedPrefs.getStringSet(RecipeFragment.Companion.KEY_FAVORITES, emptySet()) ?: emptySet()
        return stringFavorites.map { it.toInt() }.toSet()
    }

    fun setupRecycler() {
        val favoritesIds = getFavorites()
        if (favoritesIds.isEmpty()) {
            binding.tv0Favorites.visibility = View.VISIBLE
        }
        val favoritesRecipe = BackendSingleton.getRecipesByIds(favoritesIds)
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

    private fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = BackendSingleton.getRecipeById(recipeId)
        val bundle = Bundle().apply { putParcelable(RecipesListFragment.Companion.ARG_RECIPE, recipe) }
        requireActivity().supportFragmentManager.commit {
            replace<RecipeFragment>(R.id.main_container, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}