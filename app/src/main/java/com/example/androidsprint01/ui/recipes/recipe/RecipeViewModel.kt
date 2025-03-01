package com.example.androidsprint01.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsprint01.model.Recipe

class RecipeViewModel() : ViewModel() {

    data class RecipeState(
        val recipe: Recipe? = null,
        val isFavorites: Boolean = false,
        val portion: Int = 1
    )

    private val _recipeState = MutableLiveData<RecipeState>()

    val recipeState: LiveData<RecipeState>
        get() = _recipeState

    init {
        Log.i("!!!", "Initializing")
    }

    fun updateRecipe(isFavorites: Boolean) {
        _recipeState.value = _recipeState.value?.copy(isFavorites = isFavorites)
    }
}