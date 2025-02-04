package com.example.androidsprint01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidsprint01.databinding.FragmentListRecipesBinding

class RecipesListFragment : Fragment(R.layout.fragment_list_recipes) {
    private var _binding: FragmentListRecipesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding accessed before initialized")

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
        requireActivity().supportFragmentManager.commit {
            replace<RecipeFragment>(R.id.main_container)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun initRecycler() {
        binding.rvListRecipes.layoutManager = LinearLayoutManager(context)
        val recipesListAdapter =
            RecipesListAdapter(BackendSingleton.getRecipesByCategoryId(id))
        binding.rvListRecipes.adapter = recipesListAdapter

        recipesListAdapter.setOnItemClickListener(object :
            RecipesListAdapter.OnRecipeClickListener {
            override fun onRecipeItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)

            }
        })
    }
}