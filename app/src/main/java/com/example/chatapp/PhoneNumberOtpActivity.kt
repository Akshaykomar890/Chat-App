package com.example.chatapp

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.chatapp.databinding.ActivityPhoneNumberOtpBinding
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import model.chatModels
import kotlin.math.log10

class PhoneNumberOtpActivity : AppCompatActivity() {
    lateinit var binding:ActivityPhoneNumberOtpBinding
    lateinit var otp:String
    lateinit var name:String
    lateinit var number:String
    lateinit var password:String
    lateinit var setData:FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPhoneNumberOtpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        otp = intent.getStringExtra("OTP").toString()
        name = intent.getStringExtra("name").toString()
        number = intent.getStringExtra("number").toString()
        password = intent.getStringExtra("password").toString()


        binding.verifyButton.setOnClickListener {
            progressBar(true)
            val inputOtp = binding.firstOtp.text.toString() + binding.secondOtp.text.toString() + binding.thirdOtp.text.toString()+binding.fourthOtp.text.toString()+binding.fifthOtp.text.toString()+binding.sixthOtp.text.toString()
            val credential = PhoneAuthProvider.getCredential(otp,inputOtp)
            Log.d("Debug","OTP:$otp")
            Log.d("Debug2","OTP:$inputOtp")
            signInWithPhoneAuthCredential(credential)
        }


    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@PhoneNumberOtpActivity,"Registration Successful",Toast.LENGTH_SHORT).show()
                    setDat()
                    startActivity(Intent(this@PhoneNumberOtpActivity,ChatActivity::class.java))
                    finish()
                    Log.d(TAG, "signInWithCredential:success")
                } else {
                        Toast.makeText(this@PhoneNumberOtpActivity,"Enter Correct OTP",Toast.LENGTH_SHORT).show()
                    progressBar(false)
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    }
                }
            }
    }


    fun setDat(){
        setData = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val addData = chatModels(name,auth.currentUser!!.uid,number)
        setData.collection("User").document(auth.currentUser!!.uid).set(addData).addOnSuccessListener {
        }
    }

    fun progressBar(trueFalse:Boolean){
        if(trueFalse){
            binding.progressBar.visibility = View.VISIBLE
            binding.verifyButton.visibility = View.INVISIBLE
        }
        else{
            binding.progressBar.visibility = View.INVISIBLE
            binding.verifyButton.visibility = View.VISIBLE
        }
    }

}