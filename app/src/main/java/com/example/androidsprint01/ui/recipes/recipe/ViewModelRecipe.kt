package com.example.androidsprint01.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import com.example.androidsprint01.model.Recipe

class ViewModelRecipe() : ViewModel() {

    data class RecipeState(
        val recipe: Recipe? = null,
        val isFavorites: Boolean = false,
        val portion: Int? = null
    )
}