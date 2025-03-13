package com.example.androidsprint01.ui.categories

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidsprint01.data.BackendSingleton
import com.example.androidsprint01.model.Categories
import com.example.androidsprint01.ui.recipes.recipesList.RecipesListFragment

class CategoriesListViewModel(application: Application) : AndroidViewModel(application) {
    data class StateCategoriesList(
        val categoriesList: List<Categories> = emptyList<Categories>(),
    )

    val _categoriesListState = MutableLiveData(StateCategoriesList())
    val categoriesListState: LiveData<StateCategoriesList>
        get() = _categoriesListState

    fun loadCategoriesList() {
        val categories = BackendSingleton.getCategories()
        _categoriesListState.postValue(
            categoriesListState.value?.copy(
                categoriesList = categories
            )
        )

    }

    fun prepareDataForRecipesListFragment(categoryId: Int): RecipesListFragment? {
        val selectedCategory =
            _categoriesListState.value?.categoriesList?.firstOrNull { it.id == categoryId }
        if (selectedCategory != null) {
            val categoryName = selectedCategory.title
            val categoryImageUrl = selectedCategory.imageUrl

            val bundle = Bundle().apply {
                putInt(RecipesListFragment.Companion.ARG_CATEGORY_ID, categoryId)
                putString(RecipesListFragment.Companion.ARG_CATEGORY_NAME, categoryName)
                putString(RecipesListFragment.Companion.ARG_CATEGORY_IMAGE_URL, categoryImageUrl)
            }
            return RecipesListFragment().apply {
                arguments = bundle
            }
        }
        else return null
    }
}