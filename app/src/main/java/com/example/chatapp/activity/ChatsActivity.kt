package com.example.chatapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.adapter.MessageAdapter
import com.example.chatapp.databinding.ActivityChatsBinding
import com.example.chatapp.model.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Date

class ChatsActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatsBinding
    lateinit var database:FirebaseDatabase
    lateinit var senderUid:String
    lateinit var receiverUid:String
    lateinit var senderRoom:String
    lateinit var receiverRoom:String
    lateinit var recyclerView: RecyclerView
    lateinit var list:ArrayList<MessageModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChatsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        database = FirebaseDatabase.getInstance()
        senderUid = FirebaseAuth.getInstance().uid.toString()
        receiverUid = intent.getStringExtra("uid")!!
        senderRoom = senderUid+receiverUid
        receiverRoom = receiverUid+senderUid
        list = ArrayList()

        recyclerView = findViewById(R.id.showMessageRecyclerView)



        database.reference.child("chats").child(senderRoom).child("messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (snapshot1 in snapshot.children ){
                        val data = snapshot1.getValue(MessageModel::class.java)
                        list.add(data!!)
                    }
                    recyclerView.layoutManager = LinearLayoutManager(this@ChatsActivity)
                    recyclerView.adapter = MessageAdapter(this@ChatsActivity,list)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChatsActivity,"Error",Toast.LENGTH_SHORT).show()
                }

            })



        binding.chatsSendButton.setOnClickListener {
            if (binding.chatsInput.text!!.isEmpty()){
                Toast.makeText(this,"Message sent",Toast.LENGTH_SHORT).show()
            }
                val message = MessageModel(binding.chatsInput.text.toString(),senderUid, Date().time)
                val randomKey = database.reference.push().key
                database.reference.child("chats")
                    .child(senderRoom).child("messages")
                    .child(randomKey!!).setValue(message).addOnCompleteListener{
                        database.reference.child("chats").child(receiverRoom)
                            .child("messages").child(randomKey).setValue(message).addOnCompleteListener {
                                binding.chatsInput.text = null

                            }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this,"Message not  sent",Toast.LENGTH_SHORT).show()
                    }
        }
    }
}