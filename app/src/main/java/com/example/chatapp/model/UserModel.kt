package com.example.chatapp.model

data class UserModel(
    val uid:String,
    val name:String,
    val number:String,
    val imageUrl:String
){
    constructor():this("","","","")
}