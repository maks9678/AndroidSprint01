package com.example.androidsprint01.ui.recipes.recipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.model.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    data class RecipeState(
        val recipe: Recipe? = null,
        val isFavorites: Boolean = recipe?.isFavorite == true,
        val portion: Int = 1,
        val imageUrl: String = "",
    )

    private val recipeRepository: RecipeRepository = RecipeRepository(application)
    private val _recipeState = MutableLiveData(RecipeState())
    val recipeState: LiveData<RecipeState>
        get() = _recipeState

    fun updateRecipe(isFavorite: Boolean, recipeId: Int) {
        viewModelScope.launch {
            _recipeState.value?.let { currentState ->
                _recipeState.postValue(
                    currentState.copy(
                        recipe = currentState.recipe?.copy(
                            isFavorite = isFavorite
                        )
                    )
                )
                recipeRepository.updateFavoriteStatus(recipeId, isFavorite)
            }
        }
    }

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            val recipe = recipeRepository.getRecipeById(recipeId)
            recipe?.let {
                _recipeState.postValue(
                    recipeState.value?.copy(
                        recipe = it,
                        isFavorites = it.isFavorite,
                        imageUrl = it.fullImageUrl
                    )
                )
            }
        }
    }

    fun onFavoritesClicked() {
        _recipeState.value?.let { currentState ->
            updateRecipe(!currentState.isFavorites, currentState.recipe!!.id)
        }
    }

    fun updatePortion(newPortion: Int) {
        _recipeState.postValue(
            recipeState.value?.copy(portion = newPortion)
        )
    }
}