package com.example.androidsprint01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidsprint01.databinding.FragmentListRecipesBinding
import com.example.androidsprint01.databinding.FragmentListRecipesBinding.*

class RecipesListFragment : Fragment(R.layout.fragment_list_recipes) {
    private var _binding: FragmentListRecipesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            categoryId = bundle.getInt(arguments.ARG_CATEGORY_ID)
            categoryName = bundle.getString(CategoriesListFragment.ARG_CATEGORY_NAME)
            categoryImageUrl = bundle.getString(CategoriesListFragment.ARG_CATEGORY_IMAGE_URL)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}