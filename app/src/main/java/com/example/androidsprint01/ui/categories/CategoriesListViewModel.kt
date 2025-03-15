package com.example.androidsprint01.ui.categories

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidsprint01.data.BackendSingleton
import com.example.androidsprint01.model.Categories
import com.example.androidsprint01.ui.recipes.recipesList.RecipesListFragment

class CategoriesListViewModel(application: Application) : AndroidViewModel(application) {
    data class CategoriesListState(
        val categoriesList: List<Categories> = emptyList<Categories>(),
    )

    val _categoriesListState = MutableLiveData(CategoriesListState())
    val categoriesListState: LiveData<CategoriesListState>
        get() = _categoriesListState

    fun loadCategoriesList() {
        val categories = BackendSingleton.getCategories()
        _categoriesListState.postValue(
            categoriesListState.value?.copy(
                categoriesList = categories
            )
        )

    }

    fun prepareDataForRecipesListFragment(categoryId: Int): RecipesListFragment {
        val selectedCategory =
            _categoriesListState.value?.categoriesList?.firstOrNull { it.id == categoryId }
        val bundle = Bundle()
        if (selectedCategory != null) {
            val categoryName = selectedCategory.title
            val categoryImageUrl = selectedCategory.imageUrl

            bundle.apply {
                putInt(RecipesListFragment.Companion.ARG_CATEGORY_ID, categoryId)
                putString(RecipesListFragment.Companion.ARG_CATEGORY_NAME, categoryName)
                putString(RecipesListFragment.Companion.ARG_CATEGORY_IMAGE_URL, categoryImageUrl)
            }
        }
        Log.d("!!!","$bundle")
        return RecipesListFragment().apply {
            arguments = bundle
        }
    }
}