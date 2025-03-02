package com.example.androidsprint01.ui.recipes.recipe

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsprint01.R
import com.example.androidsprint01.data.BackendSingleton
import com.example.androidsprint01.data.BackendSingleton.getRecipeById
import com.example.androidsprint01.model.Recipe
import com.example.androidsprint01.ui.recipes.recipe.RecipeFragment.Companion.KEY_FAVORITES

class RecipeViewModel() : ViewModel() {

    data class RecipeState(
        val recipe: Recipe? = null,
        val isFavorites: Boolean = false,
        val portion: Int = 1
    )

    var sharedPrefs: SharedPreferences? = null
    private val _recipeState = MutableLiveData<RecipeState>()

    val recipeState: LiveData<RecipeState>
        get() = _recipeState

    init {
        Log.i("!!!", "Initializing")
        _recipeState.value = RecipeState(isFavorites = getFavorites().contains(""))
    }

    fun updateRecipe(isFavorites: Boolean) {
        _recipeState.value = _recipeState.value?.copy(isFavorites = isFavorites)
    }

    fun getFavorites(): MutableSet<String> {
        val currentFavorites =
            sharedPrefs?.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
        return HashSet(currentFavorites)
    }

    fun loadRecipe(recipeId: Int) {
        _recipeState.value =
            _recipeState.value?.copy(
                isFavorites = getFavorites().contains(recipeId.toString())
            )
        _recipeState.value = _recipeState.value?.copy(
            portion = _recipeState.value?.portion ?: 1
        )

//        TODO("load from network")
    }

    fun onFavoritesClicked() {
        _recipeState.value?.let { currentState ->
            val newFavoriteState = !currentState.isFavorites
            val updatedState = currentState.copy(isFavorites = newFavoriteState)
            _recipeState.value = updatedState
            val favorites = getFavorites()
            if (!newFavoriteState) {
                favorites.remove(updatedState.recipe?.id.toString())

            } else {
                favorites.add(updatedState.recipe?.id.toString())
            }
            saveFavorites(favorites)
        }
    }

    fun saveFavorites(favorites: Set<String>) {
        sharedPrefs?.edit()?.putStringSet(KEY_FAVORITES, favorites)?.apply()
    }
}