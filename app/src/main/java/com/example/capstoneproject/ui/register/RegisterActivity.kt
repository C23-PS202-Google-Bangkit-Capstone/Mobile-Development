package com.example.capstoneproject.ui.register

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityRegisterBinding
import com.example.capstoneproject.util.ViewModelFactory
import com.example.capstoneproject.util.repository.Result
import com.google.android.material.textfield.TextInputLayout

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

        // Mengatur action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        supportActionBar?.title = "Register"

        // Menginisialisasi TextInputLayout dan mengatur gambar ikon
        val textInputLayoutPassword: TextInputLayout = findViewById(R.id.textInputLayoutPassword)
        textInputLayoutPassword.setStartIconDrawable(R.drawable.baseline_lock_24)

        // Menginisialisasi Spinner dan mengatur teks hint
        val spinnerProvinsi: Spinner = findViewById(R.id.spinner_provinsi)

        // Tambahkan teks hint "Pilih Provinsi" sebagai item pertama dalam daftar provinsi
        val provincesWithHint = resources.getStringArray(R.array.provinces).toList()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provincesWithHint)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerProvinsi.adapter = adapter

        binding.btnSignup.setOnClickListener {
            registerClicked()
        }

        binding.tvLogin.setOnClickListener {
            onBackPressed()
        }
    }

    private fun registerClicked() {
        val username = binding.edUsername.text.toString().trim()
        val email = binding.edEmail.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()
        val phone = binding.edPhone.text.toString().trim()
        val selectedProvince = binding.spinnerProvinsi.selectedItem.toString()

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || selectedProvince == "Pilih Provinsi") {
            Toast.makeText(this, "Mohon lengkapi semua field", Toast.LENGTH_SHORT).show()
        } else {
            if (password.length < 8) {
                Toast.makeText(this, "Kata sandi harus terdiri dari minimal 8 karakter", Toast.LENGTH_SHORT).show()
            } else {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.postRegister(username, email, password, phone, selectedProvince).observe(this) {
                    when (it) {
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show()
                            onBackPressed()
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "Pendaftaran Gagal", Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
            }
        }
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
