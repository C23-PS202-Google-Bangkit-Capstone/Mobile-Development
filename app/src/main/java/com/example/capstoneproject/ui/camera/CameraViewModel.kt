package com.example.capstoneproject.ui.camera

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CameraViewModel : ViewModel() {
    val selectedImageUri: MutableLiveData<Uri> = MutableLiveData()

    fun setImageUri(uri: Uri) {
        selectedImageUri.value = uri
    }
}
