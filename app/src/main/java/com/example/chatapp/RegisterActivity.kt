package com.example.chatapp



import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import com.example.chatapp.databinding.ActivityRegisterBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import model.chatModels
import java.util.concurrent.TimeUnit


class RegisterActivity : AppCompatActivity() {
    lateinit var binding:ActivityRegisterBinding
     lateinit var number:String
     lateinit var name:String
     lateinit var password:String
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            val intent = Intent(this@RegisterActivity,ChatActivity::class.java)
            startActivity(intent)
            finish()

        } else {
        binding.registerNow.setOnClickListener {
            progressBar(true)
            number = binding.phoneNumberInputReg.text.toString()
            name = binding.nameInputRegister.text.toString()
            password = binding.passwordInputRegister.text.toString()
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(number!!)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(this)
                    .setCallbacks(callbacks)
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
        }

    }
}


    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.w(TAG, "onVerificationFailed", e)
            if (e is FirebaseAuthInvalidCredentialsException) {
            } else if (e is FirebaseTooManyRequestsException) {
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
            }
        }

        override fun onCodeSent(verificationId: String,token: PhoneAuthProvider.ForceResendingToken) {
            val intent = Intent(this@RegisterActivity,PhoneNumberOtpActivity::class.java)
            intent.putExtra("OTP",verificationId)
            intent.putExtra("name",name)
            intent.putExtra("number",number)
            intent.putExtra("password",password)
            startActivity(intent)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    startActivity(Intent(this@RegisterActivity,ChatActivity::class.java))
                    finish()

                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {

                    }
                }
            }
    }


    fun progressBar(trueFalse:Boolean){
        if(trueFalse){
            binding.progressBar.visibility = View.VISIBLE
            binding.registerNow.visibility = View.INVISIBLE
        }
        else{
            binding.progressBar.visibility = View.INVISIBLE
            binding.registerNow.visibility = View.VISIBLE
        }
    }
}