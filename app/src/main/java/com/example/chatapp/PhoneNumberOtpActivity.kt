package com.example.chatapp

import android.content.ContentValues
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
import kotlin.math.log10

class PhoneNumberOtpActivity : AppCompatActivity() {
    lateinit var binding: ActivityPhoneNumberOtpBinding
    lateinit var otp:String
    lateinit var token: PhoneAuthProvider.ForceResendingToken
    lateinit var phoneNumber:String
    lateinit var auth:FirebaseAuth
    lateinit var getData:RegisterActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPhoneNumberOtpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        otp = intent.getStringExtra("OTP").toString()
        token = intent.getParcelableExtra("resendToken")!!
        phoneNumber = intent.getStringExtra("phoneNumber")!!

        getData = RegisterActivity()

        auth = FirebaseAuth.getInstance()

        binding.verifyButton.setOnClickListener {
            val typedOtp = binding.firstOtp.text.toString()+binding.secondOtp.text.toString()+binding.thirdOtp.text.toString()+binding.fourthOtp.text.toString()+binding.fifthOtp.text.toString()+binding.sixthOtp.text.toString()
            if (typedOtp.isNotEmpty()){
                if (typedOtp.length == 6){

                    val credential:PhoneAuthCredential = PhoneAuthProvider.getCredential(otp,typedOtp)
                    signInWithPhoneAuthCredential(credential)

                    binding.progressBar.visibility = View.VISIBLE
                    binding.verifyButton.visibility = View.INVISIBLE
                }
                else{
                    Toast.makeText(this@PhoneNumberOtpActivity,"Enter Correct OTP", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this@PhoneNumberOtpActivity,"Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }



    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@PhoneNumberOtpActivity,"Verification Successful",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@PhoneNumberOtpActivity,ChatActivity::class.java)
                    startActivity(intent)
                    finish()
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                } else {
                    Toast.makeText(this@PhoneNumberOtpActivity,"Signinfailed",Toast.LENGTH_SHORT).show()
                    finish()
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@PhoneNumberOtpActivity,MainActivity::class.java)
        startActivity(intent)
    }


}