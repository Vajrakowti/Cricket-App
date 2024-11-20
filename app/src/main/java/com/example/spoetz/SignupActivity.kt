package com.example.spoetz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.spoetz.model.User

class SignupActivity : AppCompatActivity() {

    private lateinit var dbHelper: LoginDBHelper
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var genderEditText: EditText
    private lateinit var dobEditText: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        dbHelper = LoginDBHelper(this)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        emailEditText = findViewById(R.id.emailEditText)
        genderEditText = findViewById(R.id.genderEditText)
        dobEditText = findViewById(R.id.dobEditText)


        val signupButton: Button = findViewById(R.id.signupButton)

        signupButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val email = emailEditText.text.toString()
            val gender = genderEditText.text.toString()
            val dob = dobEditText.text.toString()

            val user = User(username, password, email, gender, dob)

            val result = dbHelper.addUser(user)
            if (result != -1L) {
                Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show()
                finish() // Close signup activity
            } else {
                Toast.makeText(this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show()
            }

            val explicitIntent = Intent(this,LoginActivity::class.java)
            startActivity(explicitIntent)
        }
    }
}