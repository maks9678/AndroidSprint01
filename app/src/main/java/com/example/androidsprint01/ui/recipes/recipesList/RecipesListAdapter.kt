package com.example.androidsprint01.ui.recipes.recipesList

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidsprint01.R
import com.example.androidsprint01.model.Recipe
import com.example.androidsprint01.databinding.ItemListRecipesBinding
import dagger.hilt.android.AndroidEntryPoint


class RecipesListAdapter(
    private var dataSetRecipes: List<Recipe>
) :
    RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {

    interface OnRecipeClickListener {
        fun onRecipeItemClick(id: Int)
    }

    var recipeClickListener: OnRecipeClickListener? = null

    class ViewHolder(private val binding: ItemListRecipesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.ivCardListRecipes.contentDescription = binding.root.context.getString(
                R.string.content_description_image_recipe,
                recipe.title
            )
            binding.tvCardListRecipes.text = recipe.title
            try {
                Glide.with(binding.root.context)
                    .load(recipe.fullImageUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .into(binding.ivCardListRecipes)
            } catch (e: Exception) {
                Log.e("RecipesListAdapter", "${e.message}", e)
            }
        }
    }

    fun setOnItemClickListener(listener: OnRecipeClickListener) {
        recipeClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemListRecipesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(dataSetRecipes[position])
        holder.itemView.setOnClickListener {
            recipeClickListener?.onRecipeItemClick(dataSetRecipes[position].id)
        }
    }

    override fun getItemCount(): Int = dataSetRecipes.size

    fun updateData(newData: List<Recipe>) {
        dataSetRecipes = newData
        notifyDataSetChanged()
    }
}