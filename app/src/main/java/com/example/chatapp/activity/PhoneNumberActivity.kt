package com.example.chatapp.activity

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityPhoneNumberBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import javax.security.auth.callback.Callback

class PhoneNumberActivity : AppCompatActivity() {
    lateinit var binding:ActivityPhoneNumberBinding
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPhoneNumberBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser!=null){
            startActivity(Intent(this@PhoneNumberActivity,MainActivity::class.java))
            finish()
        }

        binding.btnPhoneNumber.setOnClickListener {
            val phoneNumberInput = binding.numberInput.text.toString().trim()
            if (phoneNumberInput.isEmpty()){
                binding.numberInput.error = "Please enter 10 digit"
            }
            else{
                binding.progressBar1.visibility = View.VISIBLE
                binding.btnPhoneNumber.visibility = View.INVISIBLE
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+91"+phoneNumberInput) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding
                .setCallbacks(callbacks)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)

            }
        }


    }
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            val intent = Intent(this@PhoneNumberActivity,OtpActivity::class.java)
            intent.putExtra("otp",verificationId)
            startActivity(intent)
            finish()
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this@PhoneNumberActivity,MainActivity::class.java))
                    finish()
                    val user = task.result?.user
                } else {
                    binding.progressBar1.visibility = View.INVISIBLE
                    binding.btnPhoneNumber.visibility = View.VISIBLE
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}