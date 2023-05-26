package com.example.capstoneproject.ui.camera

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import com.example.capstoneproject.ml.ImageliteModel
import com.github.dhaval2404.imagepicker.ImagePicker
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer


class CameraFragment : Fragment() {

    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
        private const val REQUEST_CODE_CAMERA_PERMISSION = 101
        private const val REQUEST_CODE_CAMERA = 102
    }

    private lateinit var imageView: ImageView
    private lateinit var pickImageButton: Button
    private lateinit var scanButton: Button
    private val viewModel: CameraViewModel by viewModels()


    var imageProcessor = ImageProcessor.Builder()
        .add(ResizeOp(224,224, ResizeOp.ResizeMethod.BILINEAR))
        .build()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_camera, container, false)

        var label = requireActivity().application.assets.open("label.txt").bufferedReader().readLines()


        imageView = view.findViewById(R.id.imageView)
        pickImageButton = view.findViewById(R.id.pickImageButton)
        scanButton = view.findViewById(R.id.scan)

        pickImageButton.setOnClickListener {
            checkStoragePermission()
        }


        viewModel.selectedImageUri.observe(viewLifecycleOwner) { uri ->
            imageView.setImageURI(uri)

        }

        scanButton.setOnClickListener{

        viewModel.selectedImageBitmap.observe(viewLifecycleOwner){ bitmap ->
        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)


        tensorImage = imageProcessor.process(tensorImage)

        val model = ImageliteModel.newInstance(requireContext())

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(tensorImage.buffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

        var maxId = 0    
        outputFeature0.forEachIndexed{index, fl ->  
        if (outputFeature0[maxId] < fl){
            maxId = index
        }
        }    

         Toast.makeText(activity,label[maxId],Toast.LENGTH_SHORT).show()
         model.close()
         }

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
                    val imageUri: Uri? = data?.data
                    val imageBitmap: Bitmap? = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,imageUri)
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
