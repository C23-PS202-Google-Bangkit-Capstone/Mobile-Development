package com.example.capstoneproject.ui.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.databinding.ActivityDetailRecipeBinding
import com.example.capstoneproject.util.api.RecipesItem
import com.squareup.picasso.Picasso

class DetailRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRecipeBinding

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Aktifkan tombol kembali pada ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        supportActionBar?.title = "Description"


        val detail = intent.getParcelableExtra<RecipesItem>(EXTRA_DATA) as RecipesItem

        binding.apply {
            tvName.text = detail.recipeName
            tvDesc.text = detail.recipeDetail
            tvLocation.text = detail.location
            Picasso.get()
                .load(detail.photoUrl)
                .into(imageView2)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Kembali ke Fragment sebelumnya
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}