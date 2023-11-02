package com.udemy.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {


    private  lateinit var email : EditText
    private   lateinit var pass : EditText
    private   lateinit var name : EditText
    private lateinit var signBtn : Button
    private lateinit var auth: FirebaseAuth
    private lateinit var db : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        email = findViewById(R.id.emailedt)
        pass = findViewById(R.id.passwordedt)
        name = findViewById(R.id.nameedt)
        signBtn = findViewById(R.id.btnsign)
        auth = FirebaseAuth.getInstance()
        db= FirebaseDatabase.getInstance().getReference().child("user")

        signBtn.setOnClickListener{
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)

            val name= name.text.toString()
            val email = email.text.toString()
            val password = pass.text.toString()
            signUp(name,email,password)
        }

    }
    private fun signUp(identity: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                val intent = Intent(this@SignUp,MainActivity::class.java)
                finish()
                startActivity(intent)
                addDataToDB(identity,email,auth.currentUser?.uid!!)
            }
            else {
                Toast.makeText(this@SignUp,"Something went wrong",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun addDataToDB(identity: String,email: String,  uid: String?) {
        db.child(uid!!).setValue(Model(identity,email,uid))
    }
}