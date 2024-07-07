package com.example.chatapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.databinding.ReceiveMessageBinding
import com.example.chatapp.databinding.SendMessageBinding
import com.example.chatapp.model.MessageModel
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(var context: Context, var list: ArrayList<MessageModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val SENT_MESSAGE = 1
    val RECEIVE_MESSAGE = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == SENT_MESSAGE)
            SentViewHolder(LayoutInflater.from(context).inflate(R.layout.send_message,parent,false))
        else
            ReceiverViewHolder(LayoutInflater.from(context).inflate(R.layout.receive_message,parent,false))
    }

    override fun getItemViewType(position: Int): Int {
        return if (FirebaseAuth.getInstance().uid == list[position].senderId) SENT_MESSAGE else RECEIVE_MESSAGE
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = list[position]
        if (holder.itemViewType == SENT_MESSAGE){
            val viewHolder = holder as SentViewHolder
            viewHolder.binding.sendText.text = message.message
        }else{
            val viewHolder = holder as ReceiverViewHolder
            viewHolder.binding.receiveText.text = message.message
        }
    }

    class SentViewHolder(view:View):RecyclerView.ViewHolder(view){
        val binding = SendMessageBinding.bind(view)
    }
    class ReceiverViewHolder(view:View):RecyclerView.ViewHolder(view){
        val binding = ReceiveMessageBinding.bind(view)
    }

}


