package com.example.capstoneproject.ui.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.capstoneproject.R
import com.example.capstoneproject.ui.IntermezzoActivity
import com.example.capstoneproject.ui.login.LoginActivity
import com.example.capstoneproject.util.tflite.Classifier
import com.github.dhaval2404.imagepicker.ImagePicker


class CameraFragment : Fragment() {

    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
    }

    private val mInputSize = 128
    private val mModelPath = "tflite_model.tflite"
    private val mLabelPath = "label.txt"

    private lateinit var classifier: Classifier
    private lateinit var imageView: ImageView
    private lateinit var pickImageButton: Button
    private lateinit var scanButton: Button
    private lateinit var previewImageBitmap: Bitmap

    private val viewModel: CameraViewModel by viewModels()

    private fun initClassifier() {
        classifier = Classifier(requireActivity().assets, mModelPath, mLabelPath, mInputSize)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_camera, container, false)
        initClassifier()

        // Setting Action Bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Fresh Check"
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        imageView = view.findViewById(R.id.imageView)
        pickImageButton = view.findViewById(R.id.pickImageButton)
        scanButton = view.findViewById(R.id.scan)

        pickImageButton.setOnClickListener {
            checkStoragePermission()
        }

        viewModel.selectedImageUri.observe(viewLifecycleOwner) { uri ->
            imageView.setImageURI(uri)

        }

        viewModel.selectedImageBitmap.observe(viewLifecycleOwner) { bitmap ->
            previewImageBitmap = bitmap
        }

        scanButton.setOnClickListener {
            val bitmap = previewImageBitmap

            val result = classifier.recognizeImage(bitmap)

            if (result.isEmpty()) {
                Toast.makeText(activity, "Unknown", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, result[0].title, Toast.LENGTH_SHORT).show()

            }

            val Intent = Intent(activity, IntermezzoActivity::class.java)
            startActivity(Intent)
        }

        return view
    }


    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_IMAGE_PICKER) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage()
            } else {
                Toast.makeText(requireContext(), "Storage permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE_PICKER -> {

                    val imageUri: Uri? = data!!.data
                    val imageBitmap: Bitmap? = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        imageUri
                    )

                    imageUri.let {
                        viewModel.setImageUri(imageUri!!)
                    }
                    imageBitmap.let {
                        viewModel.setImageBitmap(imageBitmap!!)
                    }
                }
            }
        }
    }

}
