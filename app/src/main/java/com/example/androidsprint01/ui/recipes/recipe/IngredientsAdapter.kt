package com.example.androidsprint01.ui.recipes.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint01.databinding.ItemIngredientBinding
import com.example.androidsprint01.model.Ingredient
import java.math.BigDecimal
import java.math.RoundingMode

class IngredientsAdapter(private var dataSet: List<Ingredient> = emptyList<Ingredient>()) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    var quantity: Int = 1

    class ViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(dataSet: Ingredient, quantity: Int) {
            binding.tvIngredientName.text = dataSet.description
            val totalQuantity = BigDecimal(dataSet.quantity) * BigDecimal(quantity)
            val formatQuantity = if (totalQuantity.remainder(BigDecimal.ONE) != BigDecimal.ZERO) {
                totalQuantity.setScale(1, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()
            } else {
                totalQuantity.toInt().toString()
            }
            binding.tvIngredientAmount.text =
                "$formatQuantity ${dataSet.unitOfMeasure}"
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
        holder.bind(dataSet[position], quantity)
    }

    override fun getItemCount(): Int = dataSet.size

    fun updateData(newData: List<Ingredient>) {
        dataSet = newData
        notifyDataSetChanged()
    }

    fun updateIngredients(progress: Int) {
        quantity = progress
        notifyDataSetChanged()
    }
}