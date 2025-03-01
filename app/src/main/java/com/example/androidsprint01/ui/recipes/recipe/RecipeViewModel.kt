package com.example.androidsprint01.ui.recipes.recipe

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
                isFavorites = getFavorites().contains(recipeId.toString()))

//        TODO("load from network")
    }
}