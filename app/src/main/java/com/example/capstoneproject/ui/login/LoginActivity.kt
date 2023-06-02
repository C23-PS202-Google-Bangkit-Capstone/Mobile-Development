package com.example.capstoneproject.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.MainActivity
import com.example.capstoneproject.databinding.ActivityLoginBinding
import com.example.capstoneproject.util.ViewModelFactory
import com.example.capstoneproject.util.api.LoginRequest
import com.example.capstoneproject.util.repository.Result
import com.example.capstoneproject.util.repository.UserModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var factory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        binding.fabBack.setOnClickListener {
            backClicked()
        }
        binding.btnSignin.setOnClickListener{
            loginClicked()
        }
    }

    private fun loginClicked(){
        val credential = LoginRequest(
            binding.edEmail.text.toString(),
            binding.edPassword.text.toString()
        )

        viewModel.postLogin(credential).observe(this){
            when(it){
                is Result.Success->{
                    val response = it.data
                    println("liat "+response.loginResult.userId)
                    saveUserData(
                        UserModel(
                            response.token,
                            response.loginResult.userId,
                            response.loginResult.location,
                            true
                        )
                    )

                }
                is Result.Loading ->{

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
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun saveUserData(user: UserModel) {
        viewModel.saveUser(user)
    }
}