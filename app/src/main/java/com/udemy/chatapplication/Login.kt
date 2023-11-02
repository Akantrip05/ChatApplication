package com.udemy.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var logBtn: Button
    private lateinit var signBtn: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.emailedt)
        pass = findViewById(R.id.passwordedt)
        logBtn = findViewById(R.id.bttnlog)
        signBtn = findViewById(R.id.bttnSign)
        auth = FirebaseAuth.getInstance()

        signBtn.setOnClickListener(){
            val intent  = Intent(this@Login,SignUp::class.java)
            startActivity(intent)
        }
        logBtn.setOnClickListener(){
            val email = email.text.toString()
            val password = pass.text.toString()
            login(email,password)
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                val intent = Intent(this@Login,MainActivity::class.java)
                finish()
                startActivity(intent)
            }
            else {
                Toast.makeText(this@Login,"please signup first",Toast.LENGTH_LONG).show()
            }
        }
    }
}