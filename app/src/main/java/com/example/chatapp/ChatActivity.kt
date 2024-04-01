package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.chatapp.databinding.ActivityChatBinding

class ChatActivity :AppCompatActivity() {
    private lateinit var binding:ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val chatFragment = ChatsFragment()
        val profileFragment = ProfileFragment()
        callFragment(chatFragment)

        binding.bottomNav.setOnItemSelectedListener {
            eachItem->
            when(eachItem.itemId){
                R.id.chatMenue -> {
                    callFragment(chatFragment)
                    true
                }
                R.id.profileMenue -> {
                    callFragment(profileFragment)
                    true
                }

                else ->false
            }
        }

    }

    fun callFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commit()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}