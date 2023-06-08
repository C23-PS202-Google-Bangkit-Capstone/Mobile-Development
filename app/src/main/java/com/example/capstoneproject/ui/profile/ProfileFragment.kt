package com.example.capstoneproject.ui.profile

import android.content.DialogInterface
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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentProfileBinding
import com.example.capstoneproject.ui.home.adapter.LoadingAdapter
import com.example.capstoneproject.ui.home.adapter.RecipesAdapter
import com.example.capstoneproject.ui.login.LoginActivity
import com.example.capstoneproject.util.ViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel
    private lateinit var factory: ViewModelFactory
    private lateinit var rvRecipes: RecyclerView
    private lateinit var adapter: RecipesAdapter
    private var search: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true) // Aktifkan tampilan menu
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setting Action Bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Fresh Check"
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))


        factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]


        setUserData()
    }

    override fun onResume() {
        super.onResume()
        checkLogin()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUserData() {
        viewModel.getUser().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {

                binding.tvNama.text = user.userName
                binding.tvKota.text = user.location
                search = user.location

                adapter = RecipesAdapter()
                rvRecipes = binding.rvBookmark
                rvRecipes.setHasFixedSize(true)

                showRecyclerView()
                showListRecommendation()

            }
        }
    }

    private fun showListRecommendation() {
        binding.rvBookmark.adapter = adapter.withLoadStateFooter(
            footer = LoadingAdapter { adapter.retry() }
        )
        viewModel.getRecipesRecommendation(search!!).observe(requireActivity()) { fresh ->
            if (fresh == null) {
                binding.tvNothing.visibility = View.VISIBLE
                binding.rvBookmark.visibility = View.GONE
            } else {
                binding.tvNothing.visibility = View.GONE
                binding.rvBookmark.visibility = View.VISIBLE
                adapter.submitData(lifecycle, fresh)
            }
        }
    }


    private fun showRecyclerView() {
        rvRecipes.layoutManager = GridLayoutManager(requireContext(), 2)
        rvRecipes.setHasFixedSize(true)
        rvRecipes.adapter = adapter

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                showLogoutConfirmationDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Logout")
            .setMessage("Apakah Anda ingin keluar dari akun Anda")
            .setPositiveButton("Ya") { dialog: DialogInterface, _: Int ->
                // Lakukan logout
                performLogout()
                dialog.dismiss()
            }
            .setNegativeButton("Tidak") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun performLogout() {
        viewModel.logout()
        val loginIntent = Intent(activity, LoginActivity::class.java)
        startActivity(loginIntent)
        activity?.finish()
    }

    private fun checkLogin() {
        viewModel.getUser().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                val loginIntent = Intent(activity, LoginActivity::class.java)
                startActivity(loginIntent)
                activity?.finish() // Optional: Close the current activity after redirecting to LoginActivity
            }
        }
    }
}
