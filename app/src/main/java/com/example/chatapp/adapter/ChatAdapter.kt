package com.example.chatapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.activity.ChatsActivity
import com.example.chatapp.databinding.ChatUserItemBinding
import com.example.chatapp.model.UserModel

class ChatAdapter(var context: Context,var list:ArrayList<UserModel>)
    :RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {
    class MyViewHolder(var view: View):RecyclerView.ViewHolder(view) {
        val binding:ChatUserItemBinding = ChatUserItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val connect = LayoutInflater.from(parent.context).inflate(R.layout.chat_user_item,parent,false)
        return MyViewHolder(connect)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = list[position]
        Glide.with(context)
            .load(user.imageUrl)
            .into(holder.binding.chatImage)
        holder.binding.chatName.text = user.name

        holder.itemView.setOnClickListener {
            val intent  = Intent(context,ChatsActivity::class.java)
            intent.putExtra("uid",user.uid)
            context.startActivity(intent)
        }


    }
}