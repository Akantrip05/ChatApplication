package com.udemy.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MyAdapter(private var context: Context,private val list:ArrayList<Model>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(private val itemList : View):RecyclerView.ViewHolder(itemList) {
        val txtname = itemList.findViewById<TextView>(R.id.text)
    }
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.items,parent,false)
         return MyViewHolder(view)
     }
     override fun getItemCount(): Int {
        return list.size
     }
     override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = list[position]
         holder.txtname.text = currentUser.name

         holder.itemView.setOnClickListener{
             val intent = Intent(context,ChatActivity::class.java)
             intent.putExtra("name",currentUser.name)
             intent.putExtra("uid",currentUser.uid)
             context.startActivity(intent)

         }
     }
 }