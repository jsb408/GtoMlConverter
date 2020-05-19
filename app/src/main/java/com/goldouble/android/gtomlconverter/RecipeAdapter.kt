package com.goldouble.android.gtomlconverter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goldouble.android.gtomlconverter.db.Recipe
import com.goldouble.android.gtomlconverter.db.RecipeDB
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipeAdapter(var recipes: List<Recipe>) : RecyclerView.Adapter<RecipeAdapter.ItemViewHolder>() {
    override fun getItemCount(): Int = recipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItems(recipes[position])
    }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItems(recipe: Recipe) {
            val gText = "${recipe.g}g"
            val mlText = "${recipe.ml}mL"
            val convert = "($gText)"

            itemView.foodNameText.text = recipe.name
            itemView.convertText.text = convert
            itemView.convertedText.text = mlText

            itemView.deleteBtn.setOnClickListener {
                val recipeDB = RecipeDB.getInstance(itemView.context)
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