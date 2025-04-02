package com.example.androidsprint01.ui.recipes.recipesList

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.model.Category
import com.example.androidsprint01.model.Recipe

class RecipesListViewModel(application: Application) : AndroidViewModel(application) {


    data class RecipesListState(
        val recipesList: List<Recipe> = emptyList(),
        val category: Category = Category(0, "", "", ""),
    )

    val recipesRepository = RecipeRepository()
    private val context: Context = getApplication<Application>().applicationContext
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
            loadImage(it.imageUrl)
        }

    }

    private fun loadRecipesList() {
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


    fun loadImage(image: String): Drawable? {
        try {
            val inputStream = image.let { context.assets.open(it) }
            val drawable = Drawable.createFromStream(inputStream, null)
            return drawable
        } catch (e: Exception) {
            Log.e("RecipeViewModel", "${e.message}")
            return null
        }
    }
}