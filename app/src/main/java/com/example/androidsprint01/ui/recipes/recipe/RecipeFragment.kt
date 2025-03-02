package com.example.androidsprint01.ui.recipes.recipe

import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.androidsprint01.R
import com.example.androidsprint01.databinding.FragmentRecipeBinding
import com.example.androidsprint01.model.Recipe
import com.example.androidsprint01.ui.recipes.recipesList.RecipesListFragment
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")
    var recipe: Recipe? = null
    private var ingredientsAdapter: IngredientsAdapter? = null
    private var stepsAdapter: MethodAdapter? = null
    var sharedPrefs: SharedPreferences? = null

    val viewModel: RecipeViewModel by lazy { RecipeViewModel() }

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
            if (sharedPrefs?.getStringSet(KEY_FAVORITES, emptySet())
                    ?.contains(it.id.toString()) == true
            ) {
                binding.ibFavoritesRecipe.setImageResource(R.drawable.ic_favourites_true)
            } else {
                binding.ibFavoritesRecipe.setImageResource(R.drawable.ic_favourites)
            }
        }

        ingredientsAdapter = IngredientsAdapter(emptyList())
        stepsAdapter = MethodAdapter(emptyList())

        initUI()
        initRecycler()
    }

    private fun initUI() {

        viewModel.recipeState.observe(viewLifecycleOwner) { recipeState ->
            Log.i("!!!", "${recipeState.isFavorites}")

        }
        recipe?.let { currentRecipe ->
            viewModel.loadRecipe(currentRecipe.id)

            binding.tvRecipe.text = currentRecipe.title
            loadImageFromAssets(currentRecipe.imageUrl)


            binding.ibFavoritesRecipe.setOnClickListener {
                viewModel.onFavoritesClicked()
                updateFavoriteIcon(currentRecipe)
            }

            binding.rvIngredients.adapter = ingredientsAdapter
            binding.rvMethod.adapter = stepsAdapter
            ingredientsAdapter?.updateData(currentRecipe.ingredients)
            stepsAdapter?.updateData(currentRecipe.method)
            binding.tvNumberPortions.text = "1"
        } ?: run {
            Log.e("RecipeFragment", "Recipe is null")
        }
    }

    fun updateFavoriteIcon(currentRecipe: Recipe) {
        var isFavorite = viewModel.getFavorites().contains(currentRecipe.id.toString())
        binding.ibFavoritesRecipe.setImageResource(if (isFavorite) R.drawable.ic_favourites else R.drawable.ic_favourites_true)
    }

    private fun loadImageFromAssets(imageUrl: String?) {
        imageUrl?.let {
            try {
                requireContext().assets.open(it).use { inputStream ->
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    binding.ivHeightRecipe.setImageBitmap(bitmap)
                    binding.ivHeightRecipe.contentDescription = binding.root.context.getString(
                        R.string.content_description_image_recipe,
                        recipe?.title
                    )
                }
            } catch (e: Exception) {
                Log.e("RecipeFragment", "Ошибка загрузки изображения: ${e.message}", e)
            }
        }
    }

    fun initRecycler() {
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
                ingredientsAdapter?.updateIngredients(progress)
                binding.tvNumberPortions.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}