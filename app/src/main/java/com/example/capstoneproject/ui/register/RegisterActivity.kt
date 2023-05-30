package com.example.capstoneproject.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.MainActivity
import com.example.capstoneproject.databinding.ActivityRegisterBinding
import com.example.capstoneproject.util.ViewModelFactory
import com.example.capstoneproject.util.repository.Result

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var factory: ViewModelFactory
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        binding.btnSignup.setOnClickListener {
            registerClicked()
        }

        binding.fabBack.setOnClickListener{
            backClicked()
        }
    }

    private fun registerClicked() {
        if (binding.edPassword.text?.length!! < 8) {
            Toast.makeText(this, "SALAH", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.postRegister(
                binding.edEmail.text.toString(),
                binding.edPassword.text.toString(),
                binding.edPhone.text.toString(),
                binding.edKota.text.toString()

            ).observe(this) {
                when (it) {
                    is Result.Success -> {
                        Toast.makeText(this, "BERHASIL", Toast.LENGTH_SHORT).show()
                    }

                    is Result.Error -> {
                        Toast.makeText(this, "GAGAL", Toast.LENGTH_SHORT).show()
                    }

                    else -> {}
                }
            }
        }
    }
    private fun backClicked(){
        startActivity(Intent(this, MainActivity::class.java))
    }
}