package com.udemy.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var myadapter: MyAdapter
    private lateinit var userList: ArrayList<Model>
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference()// Change from "getReference()" to "reference"

        recyclerView = findViewById(R.id.recy)
        userList = ArrayList()
        myadapter = MyAdapter(this,userList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myadapter
        // Create a reference to the "user" node

      db.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnap in snapshot.children) {
                    val currentUser = postSnap.getValue(Model::class.java)
                    if (auth.currentUser?.uid != currentUser?.uid){
                        userList.add(currentUser!!)
                   }
                }
                myadapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error here
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.log) {
            auth.signOut()
            val intent = Intent(this@MainActivity, Login::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item) // Return the result from the superclass if the item ID is not recognized
    }
}
