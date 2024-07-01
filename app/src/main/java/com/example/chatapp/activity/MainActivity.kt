package com.example.chatapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityMainBinding
import com.example.chatapp.fragments.CallFragment
import com.example.chatapp.fragments.ChatFragment
import com.example.chatapp.fragments.StatusFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var auth: FirebaseAuth
        var binding:ActivityMainBinding
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
                R.id.statusMenue ->{
                    setFragment(StatusFragment())
                    true
                }
                R.id.callMenue ->{
                    setFragment(CallFragment())
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