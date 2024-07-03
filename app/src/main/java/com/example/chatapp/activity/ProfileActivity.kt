package com.example.chatapp.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.chatapp.databinding.ActivityProfileBinding
import com.example.chatapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.Date

class ProfileActivity : AppCompatActivity() {
    lateinit var binding:ActivityProfileBinding
    lateinit var storage:FirebaseStorage
    lateinit var database:FirebaseDatabase
    lateinit var auth:FirebaseAuth
    lateinit var selectedImg:Uri
    val getImageContract = registerForActivityResult(ActivityResultContracts.GetContent()){
        binding.circularImage.setImageURI(it)
        selectedImg = it!!
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        onBackPresed()
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.circularImage.setOnClickListener {
            getImageContract.launch("image/*")
        }


        binding.buttonProfile.setOnClickListener {
            if (binding.nameInput.text!!.isEmpty()){
                Toast.makeText(this,"Please enter your Name",Toast.LENGTH_SHORT).show()
            }
            else if (getImageContract == null){
                Toast.makeText(this,"Please select your image",Toast.LENGTH_SHORT).show()
            }
            else{
                binding.progressBar3.visibility = View.VISIBLE
                binding.buttonProfile.visibility = View.INVISIBLE
                uploadData()
            }
        }
    }

    private fun uploadData() {
        val reference = storage.reference.child("Profile").child(Date().time.toString())
        reference.putFile(selectedImg).addOnCompleteListener {
            if (it.isSuccessful){
                reference.downloadUrl.addOnSuccessListener {task->
                    uploadInfo(task.toString())
                }
            }
        }
    }

    private fun uploadInfo(imageUrl: String){
        val user = UserModel(auth.uid.toString(),binding.nameInput.text.toString(),
            auth.currentUser?.phoneNumber.toString(),imageUrl)
        database.reference.child("users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this,"Welcome",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
    }

    private fun onBackPresed() {
        binding.topAppBar.setOnClickListener {
            finish()
        }
    }
}