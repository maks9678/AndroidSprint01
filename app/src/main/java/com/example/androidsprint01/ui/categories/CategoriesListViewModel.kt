package com.example.androidsprint01.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.example.androidsprint01.data.BackendSingleton
import com.example.androidsprint01.model.Categories

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

    fun prepareDataNavDirections(categoryId: Int): NavDirections {
        val selectedCategory = _categoriesListState.value?.categoriesList?.firstOrNull{it.id == categoryId}
        if (selectedCategory != null) {
            val action = CategoriesListFragmentDirections
                .actionCategoriesListFragmentToRecipesListFragment(selectedCategory)
            return action
        }else throw IllegalArgumentException("Category with ID $categoryId does not exist")

        }
    }