package com.example.capstoneproject.ui.home

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentHomeBinding
import com.example.capstoneproject.ui.home.adapter.LoadingAdapter
import com.example.capstoneproject.ui.home.adapter.RecipesAdapter
import com.example.capstoneproject.ui.search.SearchActivity
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
        actionBar?.title = "Fresh Check"
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        setHasOptionsMenu(true)



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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_menu, menu)

//        val searchItem: MenuItem? = menu.findItem(R.id.menu_search)
//        val searchView: SearchView? = searchItem?.actionView as? SearchView

        // Mengganti judul dengan gambar dan teks
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false) // Sembunyikan judul teks
        actionBar?.setDisplayShowCustomEnabled(true) // Aktifkan tampilan kustom pada ActionBar

        val customView = layoutInflater.inflate(R.layout.custom_action_bar, null)
        val imageView = customView.findViewById<ImageView>(R.id.imageView)
        val titleTextView = customView.findViewById<TextView>(R.id.titleTextView)
        imageView.setImageResource(R.drawable.logoimage_darkgreen) // Ganti dengan sumber gambar yang Anda inginkan
        titleTextView.text = getString(R.string.fresh_check) // Ganti dengan judul yang Anda inginkan

        actionBar?.customView = customView

        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                val intent = Intent(requireActivity(), SearchActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}