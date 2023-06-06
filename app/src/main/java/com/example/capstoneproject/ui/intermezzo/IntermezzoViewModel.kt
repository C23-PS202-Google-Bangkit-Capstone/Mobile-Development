package com.example.capstoneproject.ui.intermezzo

import androidx.lifecycle.ViewModel
import com.example.capstoneproject.util.repository.Repository

class IntermezzoViewModel(private val repo: Repository) : ViewModel() {

    fun getIntermezzo(id: Int) = repo.getIntermezzo(id)
}