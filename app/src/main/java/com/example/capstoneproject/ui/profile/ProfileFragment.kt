package com.example.capstoneproject.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.capstoneproject.databinding.FragmentProfileBinding
import com.example.capstoneproject.ui.LoginActivity
import com.example.capstoneproject.ui.RegisterActivity

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
