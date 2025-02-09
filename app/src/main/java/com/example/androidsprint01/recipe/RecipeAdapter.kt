package com.example.androidsprint01.recipe

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint01.R
import com.example.androidsprint01.Recipe
import com.example.androidsprint01.databinding.ItemCategoryBinding

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
                val inputStream = binding.root.context.assets.open(recipe.imageUrl)
                val drawable = Drawable.createFromStream(inputStream, null)
                binding.ivCardCategory.setImageDrawable(drawable)
                inputStream.close()
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