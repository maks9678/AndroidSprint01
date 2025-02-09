package com.example.androidsprint01.recipesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidsprint01.BackendSingleton
import com.example.androidsprint01.R
import com.example.androidsprint01.recipe.RecipeFragment
import com.example.androidsprint01.databinding.FragmentListRecipesBinding

class RecipesListFragment : Fragment(R.layout.fragment_list_recipes) {
    companion object {
        const val ARG_CATEGORY_ID = "ARG_CATEGORY_ID"
        const val ARG_CATEGORY_NAME = "ARG_CATEGORY_NAME"
        const val ARG_CATEGORY_IMAGE_URL = "ARG_CATEGORY_IMAGE_URL"
        const val ARG_RECIPE = "ARG_RECIPE"
    }

    private var _binding: FragmentListRecipesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryId = it.getInt(ARG_CATEGORY_ID)
            categoryName = it.getString(ARG_CATEGORY_NAME)
            categoryImageUrl = it.getString(ARG_CATEGORY_IMAGE_URL)
        }
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

        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun initRecycler() {
        val recipesListAdapter: RecipesListAdapter =
            RecipesListAdapter(BackendSingleton.getRecipesByCategoryId(categoryId))
        binding.rvListRecipes.adapter = recipesListAdapter

        recipesListAdapter.setOnItemClickListener(object :
            RecipesListAdapter.OnRecipeClickListener {
            override fun onRecipeItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)

            }
        })
    }
}