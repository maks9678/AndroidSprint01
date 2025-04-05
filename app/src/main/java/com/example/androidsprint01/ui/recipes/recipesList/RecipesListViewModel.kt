package com.example.androidsprint01.ui.recipes.recipesList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.model.Category
import com.example.androidsprint01.model.Recipe
import kotlinx.coroutines.launch

class RecipesListViewModel(application: Application) : AndroidViewModel(application) {

    data class RecipesListState(
        val recipesList: List<Recipe> = emptyList(),
        val category: Category = Category(0, "", "", ""),
    )

    val recipesRepository = RecipeRepository()
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
            recipesRepository.getRecipesByIds(
                recipeListState.value?.category?.id ?: 0
            ) { recipes ->
                _recipesListState.postValue(
                    recipeListState.value?.copy(
                        recipesList = recipes
                    )
                )
            }
        }
    }
}