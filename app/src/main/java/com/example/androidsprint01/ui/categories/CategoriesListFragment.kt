package com.example.androidsprint01.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidsprint01.R
import com.example.androidsprint01.databinding.FragmentListCategoriesBinding

class CategoriesListFragment(
    val categoriesListAdapter: CategoriesListAdapter = CategoriesListAdapter(emptyList())
) : Fragment(R.layout.fragment_list_categories) {
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
        initUI()
        setupObservers()
    }

    private fun initUI() {
        binding.rvCategories.adapter = categoriesListAdapter
        categoriesListAdapter.setOnItemClickListener(object :
            CategoriesListAdapter.OnItemClickListener {

            override fun onItemClick(categoryId: Int) {
                val bundle = viewModel.prepareDataNavDirections(categoryId)

                findNavController().navigate(bundle)

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