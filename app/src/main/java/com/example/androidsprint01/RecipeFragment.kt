package com.example.androidsprint01

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint01.RecipesListFragment.Companion.ARG_RECIPE
import com.example.androidsprint01.databinding.FragmentRecipeBinding
import com.google.android.material.divider.MaterialDividerItemDecoration

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")
    var recipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(ARG_RECIPE)
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
        recipe?.let {
            binding.tvRecipe.text = it.title
        }
        initRecycler(view)
        initUI(view)

        private fun initUI(view: View) {
            val tvRecipeTitle: TextView = view.findViewById(R.id.tv_recipe)
            val ivRecipeImage: ImageView = view.findViewById(R.id.ivRecipeImage)

            tvRecipeTitle.text = recipe?.title ?: "Рецепт"
    }
    private fun initRecycler(view: View) {
        val rvIngredients: RecyclerView = view.findViewById(R.id.rv_Ingredients)
        val ingredientsAdapter = IngredientsAdapter(recipe?.ingredients?:emptyList())
        rvIngredients.adapter = ingredientsAdapter
        rvIngredients.layoutManager = LinearLayoutManager(context)

        val dividerItemDecoration = MaterialDividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        rvIngredients.addItemDecoration(dividerItemDecoration)
        val rvMethod: RecyclerView = view.findViewById(R.id.rv_Method)
        val methodAdapter = MethodAdapter(recipe?.method?:emptyList())
        rvMethod.adapter = methodAdapter
        rvMethod.layoutManager = LinearLayoutManager(context)
        rvMethod.addItemDecoration(dividerItemDecoration)
    }
}
/*
val imageView: ImageView = findViewById(R.id.your_image_view)
imageView.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
изменение цвета иконки*/
