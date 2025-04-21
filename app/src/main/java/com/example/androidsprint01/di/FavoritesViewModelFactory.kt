package com.example.androidsprint01.di

import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.ui.recipes.favorites.FavoritesViewModel

class FavoritesViewModelFactory(private val recipesRepository: RecipeRepository) :
    Factory<FavoritesViewModel> {
    override fun create(): FavoritesViewModel {
        return FavoritesViewModel(recipesRepository)
    }

}