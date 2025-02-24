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
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")
    var recycler: RecipesListAdapter? = null

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
        recycler=view.findViewById(R.id.rv_Favorites)
        setRecycler()
    }

    private fun getFavorites(): Set<Int> {

    }

    fun setRecycler() {
        val favoritesRecipe = getFavorites()
        recycler = RecipesListAdapter(getRecipesByIds(favoritesRecipe))
    }
    private fun openRecipeByRecipeId(recipeId: Int) {
        val action = FavoritesFragmentDirections.actionFavoritesToRecipeDetail(recipeId)
        findNavController().navigate(action)
    }
}