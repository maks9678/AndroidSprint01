package com.example.androidsprint01.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidsprint01.data.RecipeRepository
import com.example.androidsprint01.model.Recipe
import com.example.androidsprint01.ui.recipes.recipe.RecipeFragment.Companion.ARG_PREFERENCES
import com.example.androidsprint01.ui.recipes.recipe.RecipeFragment.Companion.KEY_FAVORITES
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    data class RecipeState(
        val recipe: Recipe? = null,
        val isFavorites: Boolean = false,
        val portion: Int = 1,
        val imageUrl: String = "",
    )

    private val recipeRepository: RecipeRepository = RecipeRepository(application)
    private val context = getApplication<Application>().applicationContext
    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(ARG_PREFERENCES, Context.MODE_PRIVATE)
    private val _recipeState = MutableLiveData(RecipeState())

    val recipeState: LiveData<RecipeState>
        get() = _recipeState

    fun updateRecipe(isFavorites: Boolean) {
        _recipeState.value?.let { currentState ->
            _recipeState.postValue(currentState.copy(isFavorites = isFavorites))

            val favorites = getFavorites()
            if (isFavorites) {
                favorites.add(currentState.recipe?.id.toString())
            } else {
                favorites.remove(currentState.recipe?.id.toString())
            }
            saveFavorites(favorites)
        }
    }

    fun getFavorites(): MutableSet<String> {
        val currentFavorites =
            sharedPrefs.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
        return HashSet(currentFavorites)
    }

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            val recipe = recipeRepository.getRecipeById(recipeId)
            recipe?.let {
                _recipeState.postValue(
                    recipeState.value?.copy(
                        recipe = it,
                        isFavorites = getFavorites().contains(recipeId.toString()),
                        imageUrl = it.fullImageUrl
                    )
                )
            }
        }
    }

    fun onFavoritesClicked() {
        _recipeState.value?.let { currentState ->
            updateRecipe(!currentState.isFavorites)
        }
    }

    fun saveFavorites(favorites: Set<String>) {
        sharedPrefs.edit().putStringSet(KEY_FAVORITES, favorites).apply()
    }

    fun updatePortion(newPortion: Int) {
        _recipeState.postValue(
            recipeState.value?.copy(portion = newPortion)
        )
    }
}