package com.example.androidsprint01.ui.recipes.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsprint01.model.Recipe
import java.util.Observer

class ViewModelRecipe() : ViewModel() {
    private var _state: MutableLiveData<String>? =null

 val state
    get() = _state ?: throw IllegalStateException("LiveData accessed before initialized")

    override fun onCleared() {

    }

    data class RecipeState(
        val recipe: Recipe? = null,
        val isFavorites: Boolean = false,
        val portion: Int = 1
    )
}