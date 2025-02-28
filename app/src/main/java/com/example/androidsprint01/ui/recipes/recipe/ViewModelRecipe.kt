package com.example.androidsprint01.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsprint01.model.Recipe

class ViewModelRecipe() : ViewModel() {
    data class RecipeState(
        val recipe: Recipe? = null,
        var isFavorites: Boolean = false,
        var portion: Int = 1
    )

    private val _state = MutableLiveData<RecipeState>()

    val state: LiveData<RecipeState>
        get() = _state

    init {
        Log.i("!!!", "Initializing")
        _state.value = RecipeState(isFavorites = false)
    }
}