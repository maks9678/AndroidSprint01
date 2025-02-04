package com.example.androidsprint01

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidsprint01.databinding.FragmentRecipeBinding

class RecipeFragment: Fragment(R.layout.fragment_recipe) {
    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get()=_binding?:throw IllegalStateException("Binding accessed before initialized")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }
}