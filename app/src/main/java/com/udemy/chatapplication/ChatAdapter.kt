package com.udemy.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class ChatAdapter(val context: Context, val megList: ArrayList<ChatModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var ITEM_RECEIVE = 1
    private var ITEM_SEND = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.receiver,parent,false)
            return ReceiveViewholder(view)
        }
        else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.sender,parent,false)
            return SentViewholder(view)
        }
    }

    override fun getItemCount(): Int {
       return megList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var currentuser = megList[position]
        if (holder.javaClass == SentViewholder::class.java)
        {
            val viewholder = holder as SentViewholder
            holder.sentMsg.text = currentuser.message
        }
        else{
            val viewholder = holder as ReceiveViewholder
            holder.receiverMsg.text = currentuser.message
        }


    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = megList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId))
        {
            return ITEM_SEND
        }
        else{

            return ITEM_RECEIVE
        }
    }
    class SentViewholder(itemView: View):RecyclerView.ViewHolder(itemView){
        val sentMsg = itemView.findViewById<TextView>(R.id.textsend)

    }
    class ReceiveViewholder(itemView: View):RecyclerView.ViewHolder(itemView){
        val receiverMsg = itemView.findViewById<TextView>(R.id.textreceiver)
    }


}