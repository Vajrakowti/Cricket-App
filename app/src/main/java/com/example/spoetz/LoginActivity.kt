package com.example.spoetz

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {

    private lateinit var dbHelper: LoginDBHelper
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    private lateinit var register : Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        dbHelper = LoginDBHelper(this)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        val loginButton: Button = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()


            if (username.isNotEmpty() && password.isNotEmpty()) {
                if (dbHelper.validateLogin(username, password)) {
                    if (dbHelper.validateLogin(username, password)) {
                        val user = dbHelper.getUserDetails(username)

                        // Store user details in SharedPreferences
                        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("username", user?.username)
                        editor.putString("email", user?.email)
                        editor.putString("gender", user?.gender)
                        editor.putString("dob", user?.dob)
                        editor.apply()


                        val intents = Intent(this, MainActivity::class.java)
                        startActivity(intents)
                    }
                } else {
                    Toast.makeText(this, "Incorrect name or password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }


        register = findViewById(R.id.register)
        register.setOnClickListener {
            val reg = Intent(this, SignupActivity::class.java)
            startActivity(reg)
        }
    }
}

