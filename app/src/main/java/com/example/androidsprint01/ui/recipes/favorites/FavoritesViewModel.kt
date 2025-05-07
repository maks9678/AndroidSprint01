package com.example.androidsprint01.ui.recipes.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(val recipeRepository: RecipeRepository) : ViewModel() {

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