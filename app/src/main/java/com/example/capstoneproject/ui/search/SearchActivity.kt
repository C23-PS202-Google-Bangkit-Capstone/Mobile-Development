package com.example.capstoneproject.ui.search

import android.R
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.databinding.ActivitySearchBinding
import com.example.capstoneproject.ui.home.HomeViewModel
import com.example.capstoneproject.ui.home.adapter.LoadingAdapter
import com.example.capstoneproject.ui.home.adapter.RecipesAdapter
import com.example.capstoneproject.util.ViewModelFactory

class SearchActivity : AppCompatActivity() {
    lateinit var binding : ActivitySearchBinding
    private lateinit var rvRecipes: RecyclerView
    private lateinit var adapter: RecipesAdapter
    private lateinit var viewModel: SearchViewModel
    private lateinit var factory: ViewModelFactory
    private var search: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        supportActionBar?.title = "Search"

        adapter = RecipesAdapter()
        rvRecipes = binding.rvRecipes
        rvRecipes.setHasFixedSize(true)

        factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

        binding.searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                search = query.toString()
                showListSearch()
                showRecyclerView()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                search = newText.toString()
                showListSearch()
                showRecyclerView()
                return true
            }


        })

    }


    private fun showListSearch() {
        binding.rvRecipes.adapter = adapter.withLoadStateFooter(
            footer = LoadingAdapter { adapter.retry() }
        )
        viewModel.getStoriesSearch(search!!).observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun showRecyclerView() {
        rvRecipes.layoutManager = GridLayoutManager(this, 2)
        rvRecipes.setHasFixedSize(true)
        rvRecipes.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}