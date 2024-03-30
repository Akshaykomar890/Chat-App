package com.example.chatapp

import android.annotation.SuppressLint
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
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import java.util.concurrent.TimeUnit


class RegisterActivity : AppCompatActivity() {
    lateinit var binding:ActivityRegisterBinding
    lateinit var auth:FirebaseAuth
    lateinit var setData:DatabaseReference
    lateinit var number:String
     lateinit var email:String
     lateinit var password: String
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goToLogin.setOnClickListener {
            startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
            finish()
        }

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null){
            val intent = Intent(this@RegisterActivity,ChatActivity::class.java)
            startActivity(intent)
            finish()
            }
        else {
            binding.registerNow.setOnClickListener {
                email = binding.emailInputRegister.text.toString()
                password = binding.passwordInputRegister.text.toString()
                number = binding.phoneNumberInputReg.text.toString()
                auth = FirebaseAuth.getInstance()
                if (email.isNotEmpty() && password.isNotEmpty() && number.isNotEmpty()) {
                    progressBar(true)
                    if (number.length == 15) {
                        val options = PhoneAuthOptions.newBuilder(auth)
                            .setPhoneNumber(number) // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(this) // Activity (for callback binding)
                            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                            .build()
                        PhoneAuthProvider.verifyPhoneNumber(options)
                    } else {
                        binding.phoneNumberInputReg.error = "Enter number"
                    }
                } else {
                    binding.emailInputRegister.error = "Enter Email"
                    binding.phoneNumberInputReg.error = "Enter Phone number"
                }
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


            //is called when the verification code is successfully sent
            // to the user's phone number during the phone number verification process.
            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken,) {
                val intent = Intent(this@RegisterActivity,PhoneNumberOtpActivity::class.java)
                intent.putExtra("OTP",verificationId)
                intent.putExtra("resendToken",token)
                intent.putExtra("phoneNumber",number)
                emailPasswordRegister(email,password)
                startActivity(intent)


            }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@RegisterActivity,"Authenticate Successful",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity,ChatActivity::class.java)
                    startActivity(intent)
                    finish()
                    Log.d(TAG, "signInWithCredential:success")
                } else {
                    Toast.makeText(this@RegisterActivity,"Sign in failed",Toast.LENGTH_SHORT).show()
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
    }


    fun emailPasswordRegister(email:String,password:String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener{
            }.addOnFailureListener{
                progressBar(false)
            }
    }





    fun progressBar(progressBar:Boolean){
        if(progressBar){
            binding.progressBar.visibility = View.VISIBLE
            binding.registerNow.visibility = View.INVISIBLE
        }
        else{
            binding.progressBar.visibility = View.INVISIBLE
            binding.registerNow.visibility = View.VISIBLE
        }
    }
    fun extractStartNameFromEmail(email: String): String {
        val pattern = Regex("^[A-Za-z]+")
        val match = pattern.find(email)
        return match?.value ?: ""
    }
}