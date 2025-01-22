package com.example.androidsprint01

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint01.databinding.ItemCategoryBinding


class CategoriesListAdapter(private val dataSet: List<Categories>) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Categories) {
            binding.tvTitle.text = category.title
            binding.tvDescription.text = category.description
            try {
                val inputStream = binding.root.context.assets.open(category.imageUrl)
                val drawable = Drawable.createFromStream(inputStream, null)
                binding.ivCard.setImageDrawable(drawable)
                inputStream.close()
            } catch (e: Exception) {
                Log.e("CategoriesListAdapter", "${e.message}", e)
            }
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}