package com.example.androidsprint01.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint01.Ingredient
import com.example.androidsprint01.databinding.ItemIngredientBinding

class IngredientsAdapter(private val dataSet: List<Ingredient>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {


    class ViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(dataSet: Ingredient) {
            binding.tvIngredientName.text = dataSet.description
            binding.tvIngredientAmount.text = "${dataSet.quantity} ${dataSet.unitOfMeasure}"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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