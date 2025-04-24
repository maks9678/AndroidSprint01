package com.example.androidsprint01.ui.recipes.recipe

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidsprint01.R
import com.example.androidsprint01.databinding.ItemCategoryBinding
import com.example.androidsprint01.model.Recipe
import dagger.hilt.android.AndroidEntryPoint


class RecipeAdapter(private val dataSet: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.tvTitleCategory.text = recipe.title
            binding.ivCardCategory.contentDescription =
                binding.root.context.getString(
                    R.string.content_description_image_category,
                    recipe.title
                )
            try {
                Glide.with(binding.root.context)
                    .load(recipe.fullImageUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .into(binding.ivCardCategory)
            } catch (e: Exception) {
                Log.e("CategoriesListAdapter", "${e.message}", e)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size
}