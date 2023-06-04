package com.example.capstoneproject.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.ui.home.HomeViewModel
import com.example.capstoneproject.ui.login.LoginViewModel
import com.example.capstoneproject.ui.profile.ProfileViewModel
import com.example.capstoneproject.ui.register.RegisterViewModel
import com.example.capstoneproject.util.di.Injection
import com.example.capstoneproject.util.repository.Repository

class ViewModelFactory(private val repo: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repo) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repo) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repo) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repo) as T
            }

            /* modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                 AddStoryViewModel(repo) as T
             }

             modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                 MapsViewModel(repo) as T
             }*/

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
        }
    }
}