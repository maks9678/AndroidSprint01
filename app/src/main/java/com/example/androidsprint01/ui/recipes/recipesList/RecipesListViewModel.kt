package com.example.androidsprint01.ui.recipes.recipesList

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidsprint01.data.BackendSingleton
import com.example.androidsprint01.model.Recipe
import com.example.androidsprint01.ui.recipes.recipesList.RecipesListFragment.Companion.ARG_CATEGORY_ID
import com.example.androidsprint01.ui.recipes.recipesList.RecipesListFragment.Companion.ARG_CATEGORY_IMAGE_URL
import com.example.androidsprint01.ui.recipes.recipesList.RecipesListFragment.Companion.ARG_CATEGORY_NAME

class RecipesListViewModel(application: Application) : AndroidViewModel(application) {


    data class RecipesListState(
        val recipesList: List<Recipe> = emptyList(),
        val categoryName: String? = null,
        val categoryImageUrl: String? = null,
        val categoryImage: Drawable? = null,
    )

    private val context: Context = getApplication<Application>().applicationContext
    private val _recipesListState = MutableLiveData(RecipesListState())
    val recipeListState: LiveData<RecipesListState>
        get() = _recipesListState
    private var categoryId: Int? = null

    fun getList(arguments: Bundle) {
        arguments.let {
            categoryId = it.getInt(ARG_CATEGORY_ID)
            loadImage(it.getString(ARG_CATEGORY_IMAGE_URL))

            _recipesListState.value = recipeListState.value?.copy(
                categoryName = it.getString(ARG_CATEGORY_NAME),
                categoryImageUrl = it.getString(ARG_CATEGORY_IMAGE_URL)
            )
        }
        loadRecipesList()
    }

    private fun loadRecipesList() {
        _recipesListState.value = recipeListState.value?.copy(
            recipesList = BackendSingleton.getRecipesByCategoryId(categoryId)
        )
    }

    private fun loadImage(image: String?) {
        try {
            val inputStream = image?.let { context.assets.open(it) }
            val drawable = Drawable.createFromStream(inputStream, null)
            _recipesListState.value = _recipesListState.value?.copy(categoryImage = drawable)
        } catch (e: Exception) {
            Log.e("RecipeViewModel", "${e.message}")
        }
    }
}