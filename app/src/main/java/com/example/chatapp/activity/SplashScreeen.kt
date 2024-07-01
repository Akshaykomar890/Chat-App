package com.example.chatapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.chatapp.R

class SplashScreeen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screeen)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreeen, PhoneNumberActivity::class.java)
            startActivity(intent)
            finish()
        },1000)
    }
}