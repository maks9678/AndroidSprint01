package com.example.androidsprint01

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint01.databinding.ItemCategoryBinding


class CategoriesListAdapter(private val dataSet: List<Categories>) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(category: Categories) {
            binding.tvTitle.text = category.title
            binding.tvDescription.text = category.description
            binding.ivCard.contentDescription =
                binding.root.context.getString(
                    R.string.content_description_image_category,
                    category.title
                )
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

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
        viewHolder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(dataSet[position].id)
        }

    }

    override fun getItemCount() = dataSet.size

}