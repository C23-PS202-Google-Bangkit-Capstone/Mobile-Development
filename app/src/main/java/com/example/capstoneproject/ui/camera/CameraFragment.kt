package com.example.capstoneproject.ui.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.capstoneproject.R
import com.example.capstoneproject.util.tflite.Classifier
import com.github.dhaval2404.imagepicker.ImagePicker
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.io.File


class CameraFragment : Fragment() {

    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
    }

    private var getFile: File? = null
    private val mInputSize = 224
    private val mModelPath = "imagelite_model.tflite"
    private val mLabelPath = "label.txt"
    private lateinit var classifier: Classifier
    private lateinit var imageView: ImageView
    private lateinit var pickImageButton: Button
    private lateinit var scanButton: Button
    private val viewModel: CameraViewModel by viewModels()
    private lateinit var previewImageBitmap: Bitmap


    private fun initClassifier() {
        classifier = Classifier(requireActivity().assets, mModelPath, mLabelPath, mInputSize)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_camera, container, false)
        initClassifier()


        imageView = view.findViewById(R.id.imageView)
        pickImageButton = view.findViewById(R.id.pickImageButton)
        scanButton = view.findViewById(R.id.scan)

        pickImageButton.setOnClickListener {
            checkStoragePermission()
        }


        viewModel.selectedImageUri.observe(viewLifecycleOwner) { uri ->
            imageView.setImageURI(uri)

        }

        viewModel.selectedImageBitmap.observe(viewLifecycleOwner){ bitmap ->
            previewImageBitmap = bitmap
        }

        scanButton.setOnClickListener{
            val bitmap = previewImageBitmap

            val result = classifier.recognizeImage(bitmap)
            Toast.makeText(activity, result[0].title, Toast.LENGTH_SHORT).show()
        }

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

                    val imageUri: Uri? = data!!.data
                    val imageBitmap: Bitmap? = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,imageUri)

                    // Add null check before casting extra to File object
                    val myFile = data.getSerializableExtra("picture") as? File

                    getFile = myFile

                    imageUri.let {
                        viewModel.setImageUri(imageUri!!)
                    }
                    imageBitmap.let {
                        viewModel.setImageBitmap(imageBitmap!!)
                    }
                    myFile?.let { // Only execute if myFile is not null
                        getFile = it
                        imageView.setImageBitmap(BitmapFactory.decodeFile(it.path))
                    }
                }
            }
        }
    }

}
