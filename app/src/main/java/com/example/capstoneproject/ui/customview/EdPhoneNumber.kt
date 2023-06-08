package com.example.capstoneproject.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class EdPhoneNumber : AppCompatEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString().trim()
                error = if (!isValidPhoneNumber(input)) {
                    "Nomor Telepon Tidak Valid"
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // Ubah sesuai dengan aturan validasi nomor telepon yang diinginkan
        val regex = Regex("^[0-9]{10,14}$")
        return regex.matches(phoneNumber)
    }
}
