package com.example.androidsprint01.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidsprint01.R
import com.example.androidsprint01.databinding.FragmentListCategoriesBinding
import com.example.androidsprint01.ui.recipes.recipesList.RecipesListFragment

class CategoriesListFragment(
    val categoriesListAdapter: CategoriesListAdapter = CategoriesListAdapter(emptyList())
) : Fragment(R.layout.fragment_list_categories) {
    companion object {
        const val  ARG_LIST_RECIPE = "ARG_LIST_RECIPE"
    }
    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")
    private val viewModel: CategoriesListViewModel by viewModels()

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
        viewModel.loadCategoriesList()
        initUI(view)
        setupObservers()
    }

    private fun initUI(view:View) {
        binding.rvCategories.adapter = categoriesListAdapter
        categoriesListAdapter.setOnItemClickListener(object :
            CategoriesListAdapter.OnItemClickListener {

            override fun onItemClick(categoryId: Int) {
                val recipesListFragment: RecipesListFragment = viewModel.prepareDataForRecipesListFragment(categoryId)
                val bundle = Bundle().apply {
                    putParcelable(ARG_LIST_RECIPE, recipesListFragment)
                }
                val button = view.findViewById<Button>(R.id.recipesListFragment,)
                button.setOnClickListener {
                    findNavController().navigate(R.id.favoritesFragment,bundle)
                }
            }
        })
    }

    private fun setupObservers() {
        viewModel.categoriesListState.observe(viewLifecycleOwner) {
            categoriesListAdapter.updateData(it.categoriesList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}