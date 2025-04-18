package com.example.androidsprint01.ui.recipes.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.model.Recipe
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeRepository = RecipeRepository(application)
    private val _favoritesState = MutableLiveData(FavoritesState())
    val favoritesState: LiveData<FavoritesState>
        get() = _favoritesState

    fun loadFavorites() {
        viewModelScope.launch {
            val favoritesList = recipeRepository.getFavoriteRecipes()
            _favoritesState.postValue(
                favoritesState.value?.copy(
                    favoritesList = favoritesList
                )
            )
        }
    }

    data class FavoritesState(
        val favoritesList: List<Recipe> = emptyList(),
    )
}