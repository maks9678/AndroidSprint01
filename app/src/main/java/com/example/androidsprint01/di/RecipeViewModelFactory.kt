package com.example.androidsprint01.di

import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.ui.recipes.recipe.RecipeViewModel

class RecipeViewModelFactory(private val recipesRepository: RecipeRepository) :
    Factory<RecipeViewModel> {
    override fun create(): RecipeViewModel {
        return RecipeViewModel(recipesRepository)
    }
}