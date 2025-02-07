package com.example.androidsprint01

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint01.databinding.ItemIngregientBinding
import com.example.androidsprint01.databinding.ItemMethodBinding

class IngredientsAdapter(private val dataSet: List<Ingredient>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {


    class ViewHolder(private val binding: ItemIngregientBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(dataSet: Ingredient) {
            binding.tvIngredientName.text = dataSet.description
            binding.tvIngredientAmount.text = dataSet.quantity + dataSet.unitOfMeasure

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = ItemIngregientBinding.
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