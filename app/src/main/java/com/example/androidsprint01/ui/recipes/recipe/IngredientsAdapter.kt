package com.example.androidsprint01.ui.recipes.recipe

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsprint01.databinding.ItemIngredientBinding
import com.example.androidsprint01.model.Ingredient
import java.math.BigDecimal
import java.math.RoundingMode
import dagger.hilt.android.AndroidEntryPoint


class IngredientsAdapter(var dataSet: List<Ingredient> = emptyList<Ingredient>()) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    var quantity: Int = 1

    class ViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(dataSet: Ingredient, quantity: Int) {
            binding.tvIngredientName.text = dataSet.description
            Log.i("!!!", "${dataSet.quantity}")
            val quantityAsBigDecimal = try {
                BigDecimal(dataSet.quantity)
            } catch (e: NumberFormatException) {
                null
            }
            if (quantityAsBigDecimal != null) {
                val totalQuantity = quantityAsBigDecimal * BigDecimal(quantity)
                val formatQuantity =
                    if (totalQuantity.remainder(BigDecimal.ONE) != BigDecimal.ZERO) {
                        totalQuantity.setScale(1, RoundingMode.HALF_UP).stripTrailingZeros()
                            .toPlainString()
                    } else {
                        totalQuantity.toInt().toString()
                    }
                binding.tvIngredientAmount.text =
                    "$formatQuantity ${dataSet.unitOfMeasure}"
            } else binding.tvIngredientAmount.text = dataSet.quantity
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
        notifyItemRangeChanged(0, dataSet.size)
    }

    fun updateIngredients(progress: Int) {
        quantity = progress
        notifyItemRangeChanged(0, dataSet.size)
    }
}