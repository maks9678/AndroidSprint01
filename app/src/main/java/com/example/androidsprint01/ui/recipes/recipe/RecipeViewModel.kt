package com.example.androidsprint01.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidsprint01.data.BackendSingleton
import com.example.androidsprint01.model.Recipe
import com.example.androidsprint01.ui.recipes.recipe.RecipeFragment.Companion.ARG_PREFERENCES
import com.example.androidsprint01.ui.recipes.recipe.RecipeFragment.Companion.KEY_FAVORITES

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    data class RecipeState(
        val recipe: Recipe? = null,
        val isFavorites: Boolean = false,
        val portion: Int = 1,
        val recipeImage: Drawable? = null,
    )

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
        val currentRecipe = BackendSingleton.getRecipeById(recipeId)

        currentRecipe.let {
            _recipeState.postValue(
                recipeState.value?.copy(
                    recipe = it,
                    isFavorites = getFavorites().contains(recipeId.toString()),
                    recipeImage = loadImage(it)
                )
            )
        }
//        TODO("load from network")
    }

    fun onFavoritesClicked() {
        _recipeState.value?.let { currentState ->
            updateRecipe(!currentState.isFavorites)
        }
    }

    fun saveFavorites(favorites: Set<String>) {
        sharedPrefs.edit().putStringSet(KEY_FAVORITES, favorites).apply()
    }

    private fun loadImage(currentRecipe: Recipe): Drawable? {
        try {
            val inputStream = context.assets.open(currentRecipe.imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            return drawable
        } catch (e: Exception) {
            Log.e("RecipeViewModel", "${e.message}")
            return null
        }
    }
    fun updatePortion(newPortion: Int) {
        _recipeState.postValue(
            recipeState.value?.copy(portion = newPortion)
        )
    }
}