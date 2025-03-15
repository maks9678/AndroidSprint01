package com.example.androidsprint01.ui.recipes.recipesList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.androidsprint01.data.BackendSingleton
import com.example.androidsprint01.R
import com.example.androidsprint01.ui.recipes.recipe.RecipeFragment
import com.example.androidsprint01.databinding.FragmentListRecipesBinding

class RecipesListFragment(
    val recipesListAdapter: RecipesListAdapter = RecipesListAdapter(emptyList()),
) : Fragment(R.layout.fragment_list_recipes) {
    companion object {
        const val ARG_CATEGORY_ID = "ARG_CATEGORY_ID"
        const val ARG_CATEGORY_NAME = "ARG_CATEGORY_NAME"
        const val ARG_CATEGORY_IMAGE_URL = "ARG_CATEGORY_IMAGE_URL"
        const val ARG_RECIPE = "ARG_RECIPE"
    }

    private var _binding: FragmentListRecipesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")
    val viewModel: RecipesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getList(arguments )
        Log.e("!!!","${arguments}")
        initRecycler()
        setupObservers()
    }

    private fun initRecycler() {
        binding.rvListRecipes.adapter = recipesListAdapter

        recipesListAdapter.setOnItemClickListener(object :
            RecipesListAdapter.OnRecipeClickListener {
            override fun onRecipeItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
    }

    private fun setupObservers() {
        viewModel.recipeListState.observe(viewLifecycleOwner, Observer { recipeListState ->
            binding.ivHeightListRecipes.setImageDrawable(recipeListState.categoryImage)
                binding.tvHeightListRecipes.text =  recipeListState.categoryName
            Log.e("!!!","${recipeListState.categoryName}")
                binding.ivHeightListRecipes.contentDescription = binding.root.context.getString(
                    R.string.content_description_image_recipe,
                    recipeListState.categoryName
                )
            recipesListAdapter.updateData(recipeListState.recipesList)
        })
    }

    fun openRecipeByRecipeId(recipeId: Int) {

        val recipe = BackendSingleton.getRecipeById(recipeId)
        val bundle = Bundle().apply { putParcelable(ARG_RECIPE, recipe) }
        requireActivity().supportFragmentManager.commit {
            replace<RecipeFragment>(R.id.main_container, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
