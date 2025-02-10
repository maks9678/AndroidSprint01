package com.example.androidsprint01.recipe

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint01.Ingredient
import com.example.androidsprint01.R
import com.example.androidsprint01.Recipe
import com.example.androidsprint01.databinding.FragmentRecipeBinding
import com.example.androidsprint01.recipesList.RecipesListFragment
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")
    var recipe: Recipe? = null

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)
        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(RecipesListFragment.Companion.ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(RecipesListFragment.Companion.ARG_RECIPE)
        }
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
            initUI()
            initRecycler()
    }

    private fun initUI() {
        recipe?.let {
            binding.tvRecipe.text = it.title
            loadImageFromAssets(it.imageUrl)
            setupIngredients(it.ingredients)
            setupCookingSteps(it.method)
        }?:run{
            Log.e("RecipeFragment", "Recipe is null")
        }
    }

    private fun loadImageFromAssets(imageUrl: String?) {
        try {
            val inputStream = requireContext().assets.open(imageUrl.toString())
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.ivHeightRecipe.setImageBitmap(bitmap)
            inputStream.close()
        } catch (e: Exception) {
            Log.e("RecipeFragment", "Ошибка загрузки изображения: ${e.message}", e)
        }

        fun setupIngredients(ingredients: List<Ingredient>) {
            val ingredientsAdapter = IngredientsAdapter(ingredients)
            binding.rvIngredients.adapter = ingredientsAdapter
        }

        private fun setupCookingSteps(steps: List<String>) {
            val stepsAdapter = MethodAdapter(steps)
            binding.rvMethod.adapter = stepsAdapter
        }

        fun initRecycler() {
            val dividerItem =
                MaterialDividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

            val rvIngredients: RecyclerView = binding.rvIngredients
            val ingredientsAdapter = IngredientsAdapter(recipe?.ingredients ?: emptyList())
            rvIngredients.adapter = ingredientsAdapter
            rvIngredients.addItemDecoration(dividerItem)

            val rvMethod: RecyclerView = binding.rvMethod
            val methodAdapter = MethodAdapter(recipe?.method ?: emptyList())
            rvMethod.adapter = methodAdapter
            rvMethod.addItemDecoration(dividerItem)
        }
    }
}