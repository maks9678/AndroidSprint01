package com.example.androidsprint01

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint01.databinding.ItemMethodBinding

class MethodAdapter(private val dataSet: List<Recipe>) :
    RecyclerView.Adapter<MethodAdapter.ViewHolder>() {


    class ViewHolder(private val binding: ItemMethodBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(dataSet: Recipe) {
            binding.tvMethodCooking.text = dataSet.method[0]
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = ItemMethodBinding.
        inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size
}