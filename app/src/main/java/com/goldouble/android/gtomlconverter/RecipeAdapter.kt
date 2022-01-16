package com.goldouble.android.gtomlconverter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goldouble.android.gtomlconverter.databinding.ItemRecipeBinding
import com.goldouble.android.gtomlconverter.db.Recipe
import com.goldouble.android.gtomlconverter.db.RecipeDB

class RecipeAdapter(var recipes: List<Recipe>) : RecyclerView.Adapter<RecipeAdapter.ItemViewHolder>() {
    override fun getItemCount(): Int = recipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItems(recipes[position])
    }

    inner class ItemViewHolder(private val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItems(recipe: Recipe) {
            val gText = "${recipe.g}g"
            val mlText = "${recipe.ml}mL"
            val convert = "($gText)"

            binding.foodNameText.text = recipe.name
            binding.convertText.text = convert
            binding.convertedText.text = mlText

            binding.deleteBtn.setOnClickListener {
                val recipeDB = RecipeDB.getInstance(binding.root.context)
                val r = Runnable {
                    recipeDB?.recipeDao()?.deleteData(recipe.id!!)
                    recipes = recipeDB?.recipeDao()?.getAll()!!
                }
                val thread = Thread(r)
                thread.start()
                notifyDataSetChanged()
            }
        }
    }
}