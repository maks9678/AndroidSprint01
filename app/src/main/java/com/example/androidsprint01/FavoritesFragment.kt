package com.example.androidsprint01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidsprint01.BackendSingleton.getRecipesByIds
import com.example.androidsprint01.databinding.FragmentFavoritesBinding
import com.example.androidsprint01.recipesList.RecipesListAdapter

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding?: throw  IllegalStateException("Binding accessed before initialized")
    var recycler: RecipesListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getFavorites():Set<Int> {

    }

    fun setRecycler() {
        val favoritesRecipe = getFavorites()
        recycler = RecipesListAdapter(getRecipesByIds(favoritesRecipe))
    }
}