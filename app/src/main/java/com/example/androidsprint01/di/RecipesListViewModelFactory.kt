package com.example.androidsprint01.di

import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.ui.recipes.recipesList.RecipesListViewModel

class RecipesListViewModelFactory (private val recipesRepository: RecipeRepository) :
    Factory<RecipesListViewModel> {
    override fun create(): RecipesListViewModel {
        return RecipesListViewModel(recipesRepository)
    }

}