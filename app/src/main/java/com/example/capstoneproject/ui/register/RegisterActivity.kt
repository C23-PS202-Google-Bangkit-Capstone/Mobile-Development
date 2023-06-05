package com.example.capstoneproject.ui.register

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.MainActivity
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
        val provinces = resources.getStringArray(R.array.provinces)

        // Tambahkan teks hint "Pilih Provinsi" sebagai item pertama dalam daftar provinsi
        val provincesWithHint = listOf("Select Province") + provinces.toList()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provincesWithHint)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerProvinsi.adapter = adapter

        binding.btnSignup.setOnClickListener {
            registerClicked()
        }

        binding.tvLogin.setOnClickListener{
            onBackPressed()
        }
    }

    private fun registerClicked() {
        if (binding.edPassword.text?.length!! < 8) {
            Toast.makeText(this, "SALAH", Toast.LENGTH_SHORT).show()
        } else {
            val selectedProvince = binding.spinnerProvinsi.selectedItem.toString() // Mengambil provinsi yang dipilih dari Spinner
            viewModel.postRegister(
                binding.edEmail.text.toString(),
                binding.edUsername.text.toString(),
                binding.edPassword.text.toString(),
                binding.edPhone.text.toString(),
                selectedProvince // Mengirim provinsi yang dipilih ke fungsi postRegister()

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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
