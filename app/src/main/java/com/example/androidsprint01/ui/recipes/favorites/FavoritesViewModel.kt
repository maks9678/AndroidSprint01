package com.example.androidsprint01.ui.recipes.favorites

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.model.Recipe
import com.example.androidsprint01.ui.recipes.recipe.RecipeFragment

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

private  val recipeRepository=RecipeRepository()
    private val _favoritesState = MutableLiveData(FavoritesState())
    val favoritesState: LiveData<FavoritesState>
        get() = _favoritesState
    val context = getApplication<Application>().getApplicationContext()
    val sharedPrefs =
        context.getSharedPreferences(RecipeFragment.Companion.ARG_PREFERENCES, Context.MODE_PRIVATE)

    private fun getFavorites(): Set<Int> {
        val stringFavorites =
            sharedPrefs.getStringSet(RecipeFragment.Companion.KEY_FAVORITES, emptySet())
                ?: emptySet()
        return stringFavorites.map { it.toInt() }.toSet()
    }

    fun loadFavorites() {
        recipeRepository.getFavoritesByIdRecipes(getFavorites()) { favoritesList->
            _favoritesState.postValue(
                favoritesState.value?.copy(
                    favoritesList = favoritesList ?: emptyList()
                )
            )
        }
    }

    data class FavoritesState(
        val favoritesList: List<Recipe> = emptyList(),
    )
}