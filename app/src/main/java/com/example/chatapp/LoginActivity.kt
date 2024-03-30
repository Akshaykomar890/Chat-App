package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.auth.ktx.auth


class LoginActivity : AppCompatActivity() {
    lateinit var emailInput:TextInputEditText
    lateinit var passwordInput:TextInputEditText
    lateinit var signIn:MaterialButton
    lateinit var bar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailInput = findViewById(R.id.emailInputLogin)
        passwordInput = findViewById(R.id.passwordInputLogin)
        bar = findViewById(R.id.progressBar)
        signIn = findViewById(R.id.signInButton)

        signIn.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                emailPasswordAuth(email, password)
                bar.visibility = View.VISIBLE
                signIn.visibility = View.INVISIBLE
            }
            else{
                emailInput.error = "Enter Email"
            }

        }


    }
    private fun emailPasswordAuth(email:String,password:String){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(this) {
            task->
            if (task.isSuccessful){
                startActivity(Intent(this@LoginActivity,ChatActivity::class.java))
                finish()
            }
            else{
                Toast.makeText(this, "Please Register", Toast.LENGTH_SHORT).show()
                bar.visibility = View.INVISIBLE
                signIn.visibility = View.VISIBLE
            }
            }
        }
    }