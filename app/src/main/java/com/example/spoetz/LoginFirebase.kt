package com.example.spoetz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginFirebase : AppCompatActivity() {

    private lateinit var edt1 : EditText
    private lateinit var edt2 : EditText
    private lateinit var login : Button
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_firebase)


        edt1 = findViewById(R.id .username)
        edt2 = findViewById(R.id.email)
        login = findViewById(R.id.logggin)

        auth = FirebaseAuth.getInstance()

        login.setOnClickListener {
            val name = edt1.text.toString()
            val email = edt2.text.toString()

            Login(name,email)
        }
    }

    fun Login(name : String,email : String){
        auth.signInWithEmailAndPassword(name,email).addOnCompleteListener { task->
            if(task.isSuccessful) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
            .addOnFailureListener { err->
                Toast.makeText(applicationContext,err.localizedMessage, Toast.LENGTH_LONG).show()

            }
    }

    fun goToRegister(view: View){
        val intent = Intent(this,RegisterFirebase::class.java)
        startActivity(intent)
    }
}