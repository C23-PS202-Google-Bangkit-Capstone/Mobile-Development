package com.example.capstoneproject.ui.profile

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.Hero
import com.example.capstoneproject.ListHeroAdapter
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentProfileBinding
import com.example.capstoneproject.ui.login.LoginActivity
import com.example.capstoneproject.ui.register.RegisterActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()

        // Setting Action Bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Fresh Check"
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        val recyclerView: RecyclerView = binding.rvBookmark
        recyclerView.setHasFixedSize(true)

        val listHero = getListHeroes()

        val listHeroAdapter = ListHeroAdapter(listHero)
        recyclerView.adapter = listHeroAdapter
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getListHeroes(): ArrayList<Hero> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listHero = ArrayList<Hero>()
        for (i in dataName.indices) {
            val hero = Hero(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listHero.add(hero)
        }
        return listHero
    }

    private fun setupButtons() {
        val loginButton = binding.btnLogin
        val registerButton = binding.btnRegister

        loginButton.setOnClickListener {
            val loginIntent = Intent(activity, LoginActivity::class.java)
            startActivity(loginIntent)
        }

        registerButton.setOnClickListener {
            val registerIntent = Intent(activity, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }
}
