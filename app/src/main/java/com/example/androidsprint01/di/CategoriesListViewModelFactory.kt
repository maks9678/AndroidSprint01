package com.example.androidsprint01.di

import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.ui.categories.CategoriesListViewModel

class CategoriesListViewModelFactory(private val recipesRepository: RecipeRepository) :
    Factory<CategoriesListViewModel> {
    override fun create(): CategoriesListViewModel {
        return CategoriesListViewModel(recipesRepository)
    }

}