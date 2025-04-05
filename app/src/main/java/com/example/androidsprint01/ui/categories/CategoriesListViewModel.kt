package com.example.androidsprint01.ui.categories

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.model.Category

class CategoriesListViewModel(application: Application) : AndroidViewModel(application) {
    data class CategoriesListState(
        val categoriesList: List<Category> = emptyList<Category>(),
    )

    val recipesRepository = RecipeRepository()
    val _categoriesListState = MutableLiveData(CategoriesListState())
    val categoriesListState: LiveData<CategoriesListState>
        get() = _categoriesListState

    fun loadCategoriesList() {
        recipesRepository.getCategories { categories ->
            if (categories != null) {
                _categoriesListState.postValue(
                    categoriesListState.value?.copy(
                        categoriesList = categories
                    )
                )
            } else {
                Log.i("!!!", "Ошибка получения категорий")
            }
        }


    }

    fun prepareDataNavDirections(categoryId: Int): NavDirections {
        val selectedCategory =
            _categoriesListState.value?.categoriesList?.firstOrNull { it.id == categoryId }
        if (selectedCategory != null) {
            val action = CategoriesListFragmentDirections
                .actionCategoriesListFragmentToRecipesListFragment(selectedCategory)
            return action
        } else throw IllegalArgumentException("Category with ID $categoryId does not exist")

    }
}