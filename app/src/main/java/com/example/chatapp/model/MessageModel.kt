package com.example.chatapp.model

import android.view.textclassifier.ConversationActions.Message
import com.google.firebase.Timestamp

data class MessageModel(
    var message: String,
    var senderId:String,
    var timestamp: Long?
){
    constructor():this("","",null)
}
