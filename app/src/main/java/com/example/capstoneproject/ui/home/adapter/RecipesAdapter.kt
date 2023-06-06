package com.example.capstoneproject.ui.home.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproject.databinding.ItemRowHeroBinding
import com.example.capstoneproject.ui.detail.DetailRecipeActivity
import com.example.capstoneproject.util.api.RecipesItem
import com.squareup.picasso.Picasso

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

    class ViewHolder(private val binding: ItemRowHeroBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipesItem) {
            with(binding) {
                tvItemName.text = recipe.recipeName
                tvItemLocation.text = recipe.location
                Picasso.get()
                    .load(recipe.photoUrl)
                    .into(ivItemPhoto)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailRecipeActivity::class.java).apply {
                    putExtra(DetailRecipeActivity.EXTRA_DATA, recipe)
                }
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivItemPhoto, "image"),
                        Pair(binding.tvItemName, "name"),
                        Pair(binding.tvItemLocation, "location"),
                    )
                it.context.startActivity(intent, optionsCompat.toBundle())
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