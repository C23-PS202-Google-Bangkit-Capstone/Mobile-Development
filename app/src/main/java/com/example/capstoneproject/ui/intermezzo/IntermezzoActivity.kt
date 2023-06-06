package com.example.capstoneproject.ui.intermezzo

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ActivityIntermezzoBinding
import com.example.capstoneproject.databinding.ActivityLoginBinding
import com.example.capstoneproject.ui.home.HomeViewModel
import com.example.capstoneproject.util.ViewModelFactory
import com.example.capstoneproject.util.repository.Result

class IntermezzoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "resultId"
        const val EXTRA_TITLE = "resultTitle"
    }

    private lateinit var binding: ActivityIntermezzoBinding
    private lateinit var viewModel: IntermezzoViewModel
    private lateinit var factory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntermezzoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aktifkan tombol kembali pada ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Mengubah warna Action Bar
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        supportActionBar?.title = "Scan Result"

        factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[IntermezzoViewModel::class.java]

        getIntermezzo()

    }

    private fun getIntermezzo(){
        val id = intent.getStringExtra(EXTRA_ID)!!.toInt()

        if (id > 16){
            binding.ivSilang.isVisible = true
            binding.ivCeklis.isVisible = false
        }
        else{
            binding.ivSilang.isVisible = false
            binding.ivCeklis.isVisible = true
        }
        val title = intent.getStringExtra(EXTRA_TITLE)
        Toast.makeText(this, id.toString(), Toast.LENGTH_SHORT).show()

        viewModel.getIntermezzo(id).observe(this){
            when (it) {
                is Result.Success -> {
                    binding.textView3.text = it.data.intermezzoResult.description
                    Toast.makeText(this, "BERHASIL", Toast.LENGTH_SHORT).show()
                }

                is Result.Error -> {
                    Toast.makeText(this, "GAGAL", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }

        }
        binding.tvStatus.text = title
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