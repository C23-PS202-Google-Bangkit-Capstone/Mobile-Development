package com.example.capstoneproject.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.Hero
import com.example.capstoneproject.ListHeroAdapter
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentHomeBinding
import com.example.capstoneproject.ui.home.adapter.LoadingAdapter
import com.example.capstoneproject.ui.home.adapter.RecipesAdapter
import com.example.capstoneproject.ui.register.RegisterViewModel
import com.example.capstoneproject.util.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var rvRecipes: RecyclerView
    private lateinit var adapter: RecipesAdapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Setting Action Bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        adapter = RecipesAdapter()
        rvRecipes = binding.rvHeroes
        rvRecipes.setHasFixedSize(true)


        factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        showRecyclerView()
        showList()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showList() {
        binding.rvHeroes.adapter = adapter.withLoadStateFooter(
            footer = LoadingAdapter { adapter.retry() }
        )
        viewModel.getStories().observe(requireActivity()) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun showRecyclerView() {
        rvRecipes.layoutManager = GridLayoutManager(requireContext(), 2)
        rvRecipes.setHasFixedSize(true)
        rvRecipes.adapter = adapter
    }
}