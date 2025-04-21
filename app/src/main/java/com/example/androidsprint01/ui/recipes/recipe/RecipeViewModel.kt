package com.example.androidsprint01.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.model.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel(val recipeRepository: RecipeRepository) : ViewModel() {

    data class RecipeState(
        val recipe: Recipe? = null,
        val portion: Int = 1,
        val imageUrl: String = "",
    )

    private val _recipeState = MutableLiveData(RecipeState())
    val recipeState: LiveData<RecipeState>
        get() = _recipeState

    fun updateRecipe(isFavorite: Boolean, recipeId: Int) {
        viewModelScope.launch {
            _recipeState.value?.let { currentState ->
                _recipeState.postValue(
                    currentState.copy(
                        recipe = currentState.recipe?.copy(
                            isFavorite = !isFavorite
                        )
                    )
                )
                recipeRepository.updateFavoriteStatus(recipeId, !isFavorite)
            }
        }
    }

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            val recipe = recipeRepository.getRecipeById(recipeId)
            Log.i("RecipeViewModel", "loadRecipe: ${recipe?.isFavorite}")
            recipe?.let {
                _recipeState.postValue(
                    recipeState.value?.copy(
                        recipe = it,
                        imageUrl = it.fullImageUrl
                    )
                )
            }
        }
    }

    fun onFavoritesClicked() {
        recipeState.value?.recipe?.let { currentRecipe ->
            updateRecipe(currentRecipe.isFavorite, currentRecipe.id)
        }
    }

    fun updatePortion(newPortion: Int) {
        _recipeState.postValue(
            recipeState.value?.copy(portion = newPortion)
        )
    }
}