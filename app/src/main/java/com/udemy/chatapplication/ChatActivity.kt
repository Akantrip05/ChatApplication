package com.udemy.chatapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var msgBox: EditText
    private lateinit var sendbtn: ImageView
    private lateinit var db: DatabaseReference
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var msgList: ArrayList<ChatModel>

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        var senderUid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        supportActionBar?.title = name
        recyclerView = findViewById(R.id.chatrecy)
        msgBox = findViewById(R.id.msgbox)
        sendbtn = findViewById(R.id.sendbtn)
        db = FirebaseDatabase.getInstance().getReference()
        msgList = ArrayList()
        chatAdapter = ChatAdapter(this, msgList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter


        db.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                msgList.clear()
                for (postSnapshot in snapshot.children)
                {
                    val message = postSnapshot.getValue(ChatModel::class.java)
                    msgList.add(message!!)
                }
                chatAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        sendbtn.setOnClickListener{
            val message = msgBox.text.toString()
           val messageObj = ChatModel(message,senderUid!!)

            db.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObj).addOnSuccessListener {
                    db.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObj)
                }
            msgBox.setText("")
        }
    }
}