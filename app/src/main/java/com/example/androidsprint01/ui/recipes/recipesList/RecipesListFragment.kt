package com.example.androidsprint01.ui.recipes.recipesList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.androidsprint01.R
import com.example.androidsprint01.RecipeApplication
import com.example.androidsprint01.databinding.FragmentListRecipesBinding

class RecipesListFragment(
    private val recipesListAdapter: RecipesListAdapter = RecipesListAdapter(emptyList()),
) : Fragment(R.layout.fragment_list_recipes) {
    private var _binding: FragmentListRecipesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")
    private lateinit var  viewModel: RecipesListViewModel
    private val args: RecipesListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appConteiner = (requireActivity().application as RecipeApplication).appConteiner
        viewModel = appConteiner.recipesListViewModelFactory.create()
    }

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
        viewModel.openRecipesByCategoryId(args.category)
        Log.e("!!!", "${arguments}")
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
            with(binding) {
                tvHeightListRecipes.text = recipeListState.category.title
                Glide.with(binding.root.context)
                    .load(recipeListState.category.fullImageUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)

                    .into(ivHeightListRecipes)
                Log.e("!!!", "${recipeListState.category}")
                ivHeightListRecipes.contentDescription = binding.root.context.getString(
                    R.string.content_description_image_recipe,
                    recipeListState.category.title
                )
            }
            recipesListAdapter.updateData(recipeListState.recipesList)
        })
    }

    fun openRecipeByRecipeId(recipeId: Int) {
        val action =
            RecipesListFragmentDirections.actionRecipesListFragmentToRecipeFragment(recipeId)

        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
