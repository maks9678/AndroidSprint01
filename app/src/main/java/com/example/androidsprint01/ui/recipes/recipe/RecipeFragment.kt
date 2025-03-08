package com.example.androidsprint01.ui.recipes.recipe

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.androidsprint01.R
import com.example.androidsprint01.data.BackendSingleton
import com.example.androidsprint01.databinding.FragmentRecipeBinding
import com.example.androidsprint01.model.Recipe
import com.example.androidsprint01.ui.recipes.recipesList.RecipesListFragment
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlin.getValue

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")
    var recipe: Recipe? = null
    private var ingredientsAdapter: IngredientsAdapter? = null
    private var stepsAdapter: MethodAdapter? = null
    var sharedPrefs: SharedPreferences? = null
    private val viewModel: RecipeViewModel by viewModels()

    companion object {
        const val ARG_PREFERENCES = "RecipePreferences"
        const val KEY_FAVORITES = "recipe_favourites"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(RecipesListFragment.Companion.ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(RecipesListFragment.Companion.ARG_RECIPE)
        }

        sharedPrefs = requireContext().getSharedPreferences(ARG_PREFERENCES, Context.MODE_PRIVATE)

        recipe?.let {
            if (viewModel.getFavorites().contains(it.id.toString())
            ) {
                binding.ibFavoritesRecipe.setImageResource(R.drawable.ic_favourites_true)
            } else {
                binding.ibFavoritesRecipe.setImageResource(R.drawable.ic_favourites)
            }
            viewModel.loadRecipe(it.id)
        }
        ingredientsAdapter = IngredientsAdapter(emptyList())
        stepsAdapter = MethodAdapter(emptyList())


        initUI()
    }

    private fun initUI() {
        val dividerItem =
            MaterialDividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        dividerItem.isLastItemDecorated = false
        dividerItem.dividerColor =
            ContextCompat.getColor(requireContext(), R.color.fon_navigation_bar)
        dividerItem.setDividerInsetStartResource(requireContext(), R.dimen._0dp)
        dividerItem.setDividerInsetEndResource(requireContext(), R.dimen._0dp)

        binding.rvIngredients.addItemDecoration(dividerItem)
        binding.rvMethod.addItemDecoration(dividerItem)
        binding.sbPortions.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                viewModel.updatePortion(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        viewModel.recipeState.observe(viewLifecycleOwner, Observer { recipeState ->
            Log.i("!!!", "${recipeState.isFavorites}")
            val recipe = recipeState.recipe ?: BackendSingleton.getRecipeById(1)

            recipe?.let { currentRecipe ->
                binding.tvRecipe.text = currentRecipe.title

                binding.tvNumberPortions.text = recipeState.portion.toString()

                recipeState.recipeImage?.let {
                    binding.ivHeightRecipe.setImageDrawable(it)
                }

                binding.ibFavoritesRecipe.setOnClickListener {
                    viewModel.onFavoritesClicked()
                    updateFavoriteIcon(currentRecipe)
                }

                binding.rvIngredients.adapter = ingredientsAdapter
                binding.rvMethod.adapter = stepsAdapter
                ingredientsAdapter?.updateData(currentRecipe.ingredients)
                stepsAdapter?.updateData(currentRecipe.method)
                Log.e("!!!", "${binding.tvNumberPortions.text}")
            } ?: run {
                Log.e("RecipeFragment", "Recipe is null")
            }
        })
    }

    fun updateFavoriteIcon(currentRecipe: Recipe) {
        var isFavorite = viewModel.getFavorites().contains(currentRecipe.id.toString())
        binding.ibFavoritesRecipe.setImageResource(if (isFavorite) R.drawable.ic_favourites_true else R.drawable.ic_favourites)
    }
}