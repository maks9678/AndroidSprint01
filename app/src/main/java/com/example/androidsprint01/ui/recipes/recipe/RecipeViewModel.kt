package com.example.androidsprint01.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(val recipeRepository: RecipeRepository) : ViewModel() {

    data class RecipeState(
        val recipe: Recipe? = null,
        val portion: Int = 1,
        val imageUrl: String = "",
    )

    private val _recipeState = MutableLiveData(RecipeState())
    val recipeState: LiveData<RecipeState>
        get() = _recipeState

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
                recipeRepository.updateRecipe(recipe)
                _recipeState.postValue(
                    recipeState.value?.copy(recipe = recipe)
                )
            }
        }

    fun loadRecipe(recipe: Recipe) {
        viewModelScope.launch {
            Log.i("RecipeViewModel", "loadRecipe: ${recipe.isFavorite}")
            _recipeState.postValue(
                recipeState.value?.copy(
                    recipe = recipe,
                    imageUrl = recipe.fullImageUrl
                )
            )
        }
    }

    fun loadRecipeFromDB(recipeId: Int){
        viewModelScope.launch {
            val recipe = recipeRepository.getRecipeById(recipeId)
            recipe?.let{
            loadRecipe(recipe)
        }}
    }

    fun onFavoritesClicked() {
        viewModelScope.launch{
        recipeState.value?.recipe?.let { currentRecipe ->
            val _updateRecipe = currentRecipe.copy(isFavorite = !currentRecipe.isFavorite)
            updateRecipe(_updateRecipe)
        }
    }}

    fun updatePortion(newPortion: Int) {
        _recipeState.postValue(
            recipeState.value?.copy(portion = newPortion)
        )
    }
}