package com.example.androidsprint01.ui.recipes.recipesList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.model.Category
import com.example.androidsprint01.model.Recipe
import kotlinx.coroutines.launch

class RecipesListViewModel(val recipeRepository: RecipeRepository) : ViewModel() {

    data class RecipesListState(
        val recipesList: List<Recipe> = emptyList(),
        val category: Category = Category(0, "", "", ""),
    )

    private val _recipesListState = MutableLiveData(RecipesListState())
    val recipeListState: LiveData<RecipesListState>
        get() = _recipesListState


    fun openRecipesByCategoryId(arguments: Category) {
        arguments.let {
            _recipesListState.setValue(
                recipeListState.value?.copy(
                    category = it
                )
            )
            loadRecipesList()
        }
    }

    private fun loadRecipesList() {
        viewModelScope.launch {
            val categoryId = recipeListState.value?.category?.id ?: 0
            val recipeCache = recipeRepository.getRecipesFromCacheById(categoryId)
            if (recipeCache.isNotEmpty()) {
                _recipesListState.postValue(
                    recipeListState.value?.copy(
                        recipesList = recipeCache
                    )
                )
                Log.i("RecipesListViewModel", "$recipeCache")
            } else Log.i("RecipesListViewModel", "Cache is empty, fetching from network")

            val recipesBackend = recipeRepository
                .getRecipesByIds(categoryId)
            if (recipesBackend.isNotEmpty()) {
                _recipesListState.postValue(recipeListState.value?.copy(recipesList = recipesBackend))
                Log.i("RecipesListViewModel", "$recipesBackend")
            } else Log.i("RecipesListViewModel", "No recipes received from backend")
        }
    }
}