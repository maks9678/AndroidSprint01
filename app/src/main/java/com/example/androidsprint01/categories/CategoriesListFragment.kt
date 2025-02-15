package com.example.androidsprint01.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.androidsprint01.BackendSingleton
import com.example.androidsprint01.R
import com.example.androidsprint01.recipesList.RecipesListFragment
import com.example.androidsprint01.databinding.FragmentListCategoriesBinding

class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {
    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun openRecipesByCategoryId(categoryId: Int) {
        val categories = BackendSingleton.getCategories()
        val selectedCategory = categories.firstOrNull { it.id == categoryId }

        if (selectedCategory != null) {
            val categoryName = selectedCategory.title
            val categoryImageUrl = selectedCategory.imageUrl

            val bundle = Bundle().apply {
                putInt(RecipesListFragment.Companion.ARG_CATEGORY_ID, categoryId)
                putString(RecipesListFragment.Companion.ARG_CATEGORY_NAME, categoryName)
                putString(RecipesListFragment.Companion.ARG_CATEGORY_IMAGE_URL, categoryImageUrl)
            }

            val recipesListFragment = RecipesListFragment().apply {
                arguments = bundle
            }

            requireActivity().supportFragmentManager.commit {
                replace(R.id.main_container, recipesListFragment)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }
    }

    private fun initRecycler() {
        val categoriesAdapter = CategoriesListAdapter(BackendSingleton.getCategories())
        binding.rvCategories.adapter = categoriesAdapter
        categoriesAdapter.setOnItemClickListener(object :
            CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)

            }

        })
    }
}