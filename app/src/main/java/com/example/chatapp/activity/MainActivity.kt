package com.example.chatapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityMainBinding
import com.example.chatapp.fragments.ProfileFragment
import com.example.chatapp.fragments.ChatFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity:AppCompatActivity(){
    lateinit var auth: FirebaseAuth
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setFragment(ChatFragment())
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser==null){
            startActivity(Intent(this@MainActivity,PhoneNumberActivity::class.java))
            finish()
        }


        binding.bottomNav.setOnItemSelectedListener {
            item->
            when(item.itemId){
                R.id.chatMenue ->{
                    setFragment(ChatFragment())
                    true
                }
                R.id.callMenue ->{
                    setFragment(ProfileFragment())
                    true
                }

                else -> {
                    false
                }
            }

        }



    }
    fun setFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment,fragment)
        transaction.commit()
    }
}