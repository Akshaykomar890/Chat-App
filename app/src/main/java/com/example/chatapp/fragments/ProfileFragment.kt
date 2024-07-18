package com.example.chatapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.model.UserModel
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView


class ProfileFragment : Fragment() {
    lateinit var database:FirebaseDatabase
    lateinit var auth:FirebaseAuth


   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_profile, container, false)
       database = FirebaseDatabase.getInstance()
       auth = FirebaseAuth.getInstance()

       val image:CircleImageView = view.findViewById(R.id.profileImage)
       val name:MaterialTextView = view.findViewById(R.id.profileName)

       database.reference.child("users")
           .child(auth.uid.toString()).get().addOnSuccessListener {
               val get = it.getValue(UserModel::class.java)
               Glide.with(view.context)
                   .load(get?.imageUrl)
                   .into(image)
               name.text = get?.name
           }

       return view
    }
}


