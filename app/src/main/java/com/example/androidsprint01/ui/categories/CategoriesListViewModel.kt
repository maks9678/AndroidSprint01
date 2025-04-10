package com.example.androidsprint01.ui.categories

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.androidsprint01.data.AppDatabase
import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.model.Category
import kotlinx.coroutines.launch

class CategoriesListViewModel(application: Application) : AndroidViewModel(application) {
    data class CategoriesListState(
        val categoriesList: List<Category> = emptyList<Category>(),
    )

    val database = AppDatabase.getCategoriesDatabase(application.applicationContext)


    val recipesRepository = RecipeRepository()
    val _categoriesListState = MutableLiveData(CategoriesListState())
    val categoriesListState: LiveData<CategoriesListState>
        get() = _categoriesListState


    fun loadCategoriesList() {
        viewModelScope.launch {
            val categoriesCache = recipesRepository.getCategoriesFromCache(database = database)
            if (categoriesCache.isNotEmpty()) {
                _categoriesListState.postValue(
                    categoriesListState.value?.copy(
                        categoriesList = categoriesCache
                    )
                )
            }
                val categoriesBackend = recipesRepository.getCategories()
                Log.d("!!!", "Categories from backend: ${categoriesBackend?.size}")
                if (categoriesBackend != null) {
                    database.categoryDao().insertAllCategories(categoriesBackend)
                    _categoriesListState.postValue(
                        categoriesListState.value?.copy(
                            categoriesList = categoriesBackend
                        )
                    )

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