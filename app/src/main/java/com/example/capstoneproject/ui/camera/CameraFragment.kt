package com.example.capstoneproject.ui.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.capstoneproject.R
import com.github.dhaval2404.imagepicker.ImagePicker

class CameraFragment : Fragment() {

    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
        private const val REQUEST_CODE_CAMERA_PERMISSION = 101
        private const val REQUEST_CODE_CAMERA = 102
    }

    private lateinit var imageView: ImageView
    private lateinit var pickImageButton: Button

    private val viewModel: CameraViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_camera, container, false)

        imageView = view.findViewById(R.id.imageView)
        pickImageButton = view.findViewById(R.id.pickImageButton)

        pickImageButton.setOnClickListener {
            checkStoragePermission()
        }

        viewModel.selectedImageUri.observe(viewLifecycleOwner, { uri ->
            imageView.setImageURI(uri)
        })

        return view
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            pickImage()
        } else {
            requestStoragePermission()
        }
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_CODE_IMAGE_PICKER
        )
    }

    private fun pickImage() {
        ImagePicker.with(this)
            .start(REQUEST_CODE_IMAGE_PICKER)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_IMAGE_PICKER) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage()
            } else {
                Toast.makeText(requireContext(), "Storage permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE_PICKER -> {
                    val imageUri: Uri? = data?.data
                    imageUri?.let {
                        viewModel.setImageUri(imageUri)
                    }
                }
            }
        }
    }
}
