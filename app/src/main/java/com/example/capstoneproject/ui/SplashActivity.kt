package com.example.capstoneproject.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.MainActivity
import com.example.capstoneproject.R

class SplashActivity : AppCompatActivity() {

    private val animDuration = 500L // Duration of the pop-up animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashLogo: ImageView = findViewById(R.id.splash_logo) // Find ImageView by its id

        val animPopUp: Animation = AnimationUtils.loadAnimation(this, R.anim.pop_up_anim)
        animPopUp.duration = animDuration

        // Run the pop-up animation on the ImageView
        splashLogo.startAnimation(animPopUp)

        // Set a handler to perform actions after the animation finishes
        animPopUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                Handler().postDelayed({
                    // Switch to the next page after a delay
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 2000) // Delay in milliseconds (2 seconds)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
}
