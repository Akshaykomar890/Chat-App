package com.example.chatapp

import Adapter.ChatsAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.combine
import model.chatModels

class ChatsFragment : Fragment() {
    lateinit var adapters: ChatsAdapter
    lateinit var setData: FirebaseFirestore
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setData = FirebaseFirestore.getInstance()
        val view = inflater.inflate(R.layout.fragment_chats, container, false)
        recyclerView = view.findViewById(R.id.chatsRecyclerView)
        setDatas()

        return view

        return inflater.inflate(R.layout.fragment_chats, container, false)


    }

    fun setDatas() {
        setData.collection("User").get().addOnSuccessListener {
            val getData = it.toObjects(chatModels::class.java)
            adapters = ChatsAdapter(getData)
            recyclerView.layoutManager = LinearLayoutManager(this@ChatsFragment.context)
            recyclerView.adapter = adapters
        }

    }
}