package com.example.capstoneproject.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.databinding.ItemRowHeroBinding
import com.example.capstoneproject.util.api.RecipesItem

class RecipesAdapter : PagingDataAdapter<RecipesItem, RecipesAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRowHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class ViewHolder(val binding: ItemRowHeroBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipesItem) {
            with(binding) {
                tvItemName.text = recipe.recipeName
                tvItemLocation.text = recipe.location
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecipesItem>() {
            override fun areItemsTheSame(oldItem: RecipesItem, newItem: RecipesItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RecipesItem,
                newItem: RecipesItem
            ): Boolean {
                return oldItem.recipeId == newItem.recipeId
            }
        }
    }
}