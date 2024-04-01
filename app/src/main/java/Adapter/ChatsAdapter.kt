package Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.MessageActivity
import com.example.chatapp.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import model.chatModels


class ChatsAdapter(val data:List<chatModels>)
    :RecyclerView.Adapter<ChatsAdapter.MyViewHolder>() {
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var displayName:TextView = itemView.findViewById(R.id.chatNameText)
        val displayMessage:TextView = itemView.findViewById(R.id.chatMessageText)

        fun eachClick (id:String){
            FirebaseFirestore.getInstance().collection("User")
                .document(id).get().addOnSuccessListener {
                    val get = it.toObject(chatModels::class.java)
                    if (get != null){

                        itemView.setOnClickListener {
                            it.context.startActivities(arrayOf(Intent(itemView.context,MessageActivity::class.java)))
                        }
                    }
                }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val connect = LayoutInflater.from(parent.context).inflate(R.layout.chat_message_fragment,parent,false)
        return MyViewHolder(connect)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val getPosition = data[position]
        holder.displayName.text = getPosition.number

        holder.eachClick(getPosition.id.toString())
    }

}