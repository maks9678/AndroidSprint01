package com.example.androidsprint01

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.androidsprint01.BackendSingleton.getRecipesByIds
import com.example.androidsprint01.databinding.FragmentFavoritesBinding
import com.example.androidsprint01.recipe.RecipeFragment
import com.example.androidsprint01.recipe.RecipeFragment.Companion.ARG_PREFERENCES
import com.example.androidsprint01.recipe.RecipeFragment.Companion.KEY_FAVORITES
import com.example.androidsprint01.recipesList.RecipesListAdapter
import com.example.androidsprint01.recipesList.RecipesListFragment.Companion.ARG_RECIPE

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")
    private var favoritesAdapter: RecipesListAdapter? = null
    private val sharedPrefs: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(
            ARG_PREFERENCES,
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
        val stringFavorites = sharedPrefs.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
        return stringFavorites.map { it.toInt() }.toSet()
    }

    fun setupRecycler() {
        val favoritesIds = getFavorites()
        if (favoritesIds.isEmpty()) {
            binding.tv0Favorites.visibility = View.VISIBLE
        }
        val favoritesRecipe = getRecipesByIds(favoritesIds)
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
        val bundle = Bundle().apply { putParcelable(ARG_RECIPE, recipe) }
        requireActivity().supportFragmentManager.commit {
            replace<RecipeFragment>(R.id.main_container, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}