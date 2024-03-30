package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapp.databinding.WelcomeScreen1Binding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: WelcomeScreen1Binding
    lateinit var get:SplashScreen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WelcomeScreen1Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.welcomeLoginButton.setOnClickListener {
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
        }
        binding.welcomeRegisterButton.setOnClickListener {
            startActivity(Intent(this@MainActivity,RegisterActivity::class.java))
        }
    }
}