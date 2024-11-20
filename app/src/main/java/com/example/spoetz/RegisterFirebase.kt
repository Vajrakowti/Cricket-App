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

class RegisterFirebase : AppCompatActivity() {

    private lateinit var edt1 : EditText
    private lateinit var edt2 : EditText
    private lateinit var res : Button
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_firebase)

        edt1 = findViewById(R.id .username)
        edt2 = findViewById(R.id.email)
        res = findViewById(R.id.register)

        auth = FirebaseAuth.getInstance()

        res.setOnClickListener {
            val name = edt1.text.toString()
            val email = edt2.text.toString()

            Register(name,email)
        }
    }

    fun Register(name : String,email : String){

        auth.createUserWithEmailAndPassword(name,email).addOnCompleteListener { task->
            if(task.isSuccessful) {
                val intent = Intent(this, LoginFirebase::class.java)
                startActivity(intent)
                finish()
            }
        }
            .addOnFailureListener { err->
                Toast.makeText(applicationContext,err.localizedMessage, Toast.LENGTH_LONG).show()

            }
    }

    fun goToLogin(view: View){
        val intent = Intent(this,LoginFirebase::class.java)
        startActivity(intent)
    }
}