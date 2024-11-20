package com.example.spoetz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spoetz.adapter.CartAdapter
import com.example.spoetz.model.CartItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var dbHelper: DatabaseHelper
    private var cartItems: MutableList<CartItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)


        recyclerView = findViewById(R.id.cartRecyclerView)  // Ensure RecyclerView is initialized
        dbHelper = DatabaseHelper(this)

        // Wrapping the database fetch and RecyclerView setup in try-catch
        try {
            // Fetching data from the database
            cartItems = dbHelper.getAllItems().toMutableList()

            // Check if the cartItems list is empty
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show()
            }

            // Setting up RecyclerView
            cartAdapter = CartAdapter(cartItems,dbHelper)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = cartAdapter

        } catch (e: Exception) {
            // Log the error message and display it for debugging purposes
            Log.e("CartActivity", "Error in CartActivity: ${e.message}")
            e.printStackTrace()
            // Optionally, you can show a Toast message if an error occurs
            Toast.makeText(this, "Failed to load cart items", Toast.LENGTH_SHORT).show()
        }


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.bottom_cart

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right)
                    finish()
                    true
                }
                R.id.bottom_search -> {
                    startActivity(Intent(applicationContext, ExploreActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right)
                    finish()
                    true
                }
                R.id.bottom_cart -> true
                R.id.bottom_profile -> {
                    startActivity(Intent(applicationContext, ProfileActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}