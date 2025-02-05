package com.example.androidsprint01

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint01.databinding.ItemListRecipesBinding

class RecipesListAdapter(private val dataSetRecipes: List<Recipe>) :
    RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {

    interface OnRecipeClickListener {
        fun onRecipeItemClick(id:Int)
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
                val inputStream = binding.root.context.assets.open(recipe.imageUrl)
                val drawable = Drawable.createFromStream(inputStream, null)
                binding.ivCardListRecipes.setImageDrawable(drawable)
                inputStream.close()
            } catch (e: Exception) {
                Log.e("RecipesListAdapter", "${e.message}", e)
            }
        }
    }
    fun setOnItemClickListener(listener: OnRecipeClickListener) {
         recipeClickListener= listener
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
}