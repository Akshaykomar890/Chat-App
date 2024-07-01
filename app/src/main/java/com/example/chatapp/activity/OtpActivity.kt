package com.example.chatapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OtpActivity : AppCompatActivity() {
    lateinit var binding: ActivityOtpBinding
    lateinit var auth: FirebaseAuth
    var otp: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOtpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        otp = intent.getStringExtra("otp").toString()
        auth = FirebaseAuth.getInstance()
        setupOtpEditText(binding.number1,binding.number2)
        setupOtpEditText(binding.number2,binding.number3)
        setupOtpEditText(binding.number3,binding.number4)
        setupOtpEditText(binding.number4,binding.number5)
        setupOtpEditText(binding.number5,binding.number6)



        binding.verifyBtn.setOnClickListener {
            val input = binding.number1.text.toString() + binding.number2.text.toString() + binding.number3.text.toString() + binding.number4.text.toString() + binding.number5.text.toString() + binding.number6.text.toString()
            if (input.isEmpty()) {
                Toast.makeText(this@OtpActivity, "Please enter OTP", Toast.LENGTH_SHORT).show()
            } else {
                binding.progressBar2.visibility = View.VISIBLE
                binding.verifyBtn.visibility = View.INVISIBLE
                val credential = PhoneAuthProvider.getCredential(otp, input)
                signInWithPhoneAuthCredential(credential)
            }

        }
    }



    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this@OtpActivity, MainActivity::class.java))
                    finish()
                    val user = task.result?.user
                } else {
                    binding.progressBar2.visibility = View.INVISIBLE
                    binding.verifyBtn.visibility = View.VISIBLE
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    }
                }
            }
    }

    private fun setupOtpEditText(currentEditText: EditText, nextEditText: EditText) {
        currentEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No action needed
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 1) {
                    nextEditText.requestFocus()
                }
            }
        })

    }

}