package com.example.androidsprint01.ui.recipes.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint01.databinding.ItemMethodBinding

class MethodAdapter(var dataSet: List<String> = emptyList<String>(),) :
    RecyclerView.Adapter<MethodAdapter.ViewHolder>() {


    class ViewHolder(private val binding: ItemMethodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(stringMethod: String) {
            binding.tvMethodCooking.text = stringMethod
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemMethodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind("${position + 1}. ${dataSet[position]}")
    }

    override fun getItemCount(): Int = dataSet.size
    fun updateData(newData: List<String>) {
        dataSet = newData
        notifyItemRangeChanged(0, dataSet.size)
    }
}