package com.example.androidsprint01.ui.recipes.recipesList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.model.Category
import com.example.androidsprint01.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesListViewModel @Inject constructor(val recipeRepository: RecipeRepository) : ViewModel() {

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
            Log.i("RecipesListViewModel", "$categoryId")
            try {
            val recipes = recipeRepository.getRecipeListFromCache(categoryId)

            _recipesListState.postValue(
                recipeListState.value?.copy(
                    recipesList = recipes
                )
            )
            Log.i("RecipesListViewModel", "$recipes")
            val recipesBackend = recipeRepository
                .getRecipesByIds(categoryId)
                Log.i("RecipesListViewModel", "$recipesBackend")
            if (recipesBackend.isNotEmpty()) {
                _recipesListState.postValue(recipeListState.value?.copy(recipesList = recipesBackend))
                Log.i("RecipesListViewModel", "$recipesBackend")
            } else Log.i("RecipesListViewModel", "No recipes received from backend")
        }catch (e:Exception){
                Log.i("RecipesListViewModel", "$e")
        }
        }
    }
}