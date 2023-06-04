package com.example.capstoneproject.ui.login

import com.example.capstoneproject.ui.register.RegisterActivity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.MainActivity
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityLoginBinding
import com.example.capstoneproject.util.ViewModelFactory
import com.example.capstoneproject.util.api.LoginRequest
import com.example.capstoneproject.util.repository.Result
import com.example.capstoneproject.util.repository.UserModel
import com.google.android.material.textfield.TextInputLayout

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var factory: ViewModelFactory
    private var shouldRedirectToHome = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        //dibawah ini UI
        val textInputLayoutEmail: TextInputLayout = findViewById(R.id.textInputLayoutEmail)
        textInputLayoutEmail.setStartIconDrawable(R.drawable.baseline_email_24)

        val textInputLayoutPassword: TextInputLayout = findViewById(R.id.textInputLayoutPassword)
        textInputLayoutPassword.setStartIconDrawable(R.drawable.baseline_lock_24)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        supportActionBar?.title = "Log In"
        //diatas ini UI

        binding.btnSignin.setOnClickListener {
            loginClicked()
        }
        binding.tvToregister.setOnClickListener{
            val intentToregister = Intent(this, RegisterActivity::class.java)
            startActivity(intentToregister)
        }
    }

    override fun onBackPressed() {
        backClicked()
    }

    private fun loginClicked() {
        val credential = LoginRequest(
            binding.edEmail.text.toString(),
            binding.edPassword.text.toString()
        )

        viewModel.postLogin(credential).observe(this) {
            when (it) {
                is Result.Success -> {
                    val response = it.data
                    saveUserData(
                        UserModel(
                            response.token,
                            response.loginResult.userId,
                            response.loginResult.location,
                            true
                        )
                    )
                    shouldRedirectToHome = true
                    navigateToHome()
                }

                is Result.Loading -> {
                    // Handle loading state if needed
                }

                is Result.Error -> {
                    Toast.makeText(this, "Login gagal", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun backClicked() {
        navigateToHome()
    }

    private fun saveUserData(user: UserModel) {
        viewModel.saveUser(user)
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
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
