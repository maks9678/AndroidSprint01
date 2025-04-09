package com.example.androidsprint01.ui.categories

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidsprint01.R
import com.example.androidsprint01.databinding.ItemCategoryBinding
import com.example.androidsprint01.model.Category

class CategoriesListAdapter(private var dataSet: List<Category> = emptyList()) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(category: Category) {
            binding.tvTitleCategory.text = category.title
            binding.tvDescriptionCategory.text = category.description
            binding.ivCardCategory.contentDescription =
                binding.root.context.getString(
                    R.string.content_description_image_category,
                    category.title
                )
            try {
                Glide.with(binding.root.context)
                    .load(category.fullImageUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .into(binding.ivCardCategory)
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

    fun updateData(newData: List<Category>) {

        dataSet = newData
        notifyItemRangeChanged(0, dataSet.size)
    }
}