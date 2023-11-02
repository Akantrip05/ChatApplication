package com.udemy.chatapplication

class ChatModel {
    var message : String? = null
    var senderId :String? = null

constructor(){}
    constructor(message: String,senderId:String)
    {
        this.message = message
        this.senderId = senderId
    }
}