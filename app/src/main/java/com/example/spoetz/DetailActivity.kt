package com.example.spoetz

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.spoetz.model.Item
import com.example.spoetz.model.ItemResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class DetailActivity : AppCompatActivity() {

    private lateinit var itemText: TextView
    private lateinit var price: TextView
    private lateinit var rating: TextView
    private lateinit var description: TextView
    private lateinit var image: ImageView

    private lateinit var addToCartButton: Button
    private lateinit var pay : Button
    private lateinit var dbHelper: DatabaseHelper





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        itemText = findViewById(R.id.itemText)
        price    = findViewById(R.id.price)
        rating   = findViewById(R.id.rating)
        description = findViewById(R.id.desc)
        image = findViewById(R.id.detailImage)

        addToCartButton = findViewById(R.id.ADDTOCART)
        pay = findViewById(R.id.buyNow)

        pay.setOnClickListener {
            val intent  = Intent(this,PaymentMode::class.java)
            startActivity(intent)
        }



        // Get movie ID from intent extras
        val movieId = intent.getIntExtra("MOVIE_ID", -1)
        if (movieId != -1) {
            fetchMovieDetails(movieId)
        }

        dbHelper = DatabaseHelper(this)

        addToCartButton.setOnClickListener {

            val itemTitle = itemText.text.toString()
            val itemPrice = price.text.toString()

            // Get the Bitmap from the ImageView
            val drawable = image.drawable
            val bitmap = (drawable as? BitmapDrawable)?.bitmap

            if (bitmap != null) {
                // Convert Bitmap to byte array
                val byteArray = bitmapToByteArray(bitmap)

                // Save item to SQLite (store both title and image byte array)
                dbHelper.addItemToCart(itemTitle, byteArray, itemPrice)

                // Optionally, show a Toast to confirm
                Toast.makeText(this, "Item added to cart", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Image is not available", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun fetchMovieDetails(movieId: Int) {
        val apiService = ApiService.create()
        apiService.getMovies().enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    val movies = response.body()
                    val movie = movies?.find { it.id == movieId }
                    if (movie != null) {
                        displayMovieDetails(movie)
                    }
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                Log.e("DetailActivity", "Failed to fetch data: ${t.message}")
            }
        })
    }


    private fun displayMovieDetails(item: Item) {
        itemText.text = item.item
        price.text = item.price
        rating.text = item.rating
        description.text = item.description.joinToString(", ")
        Glide.with(this)
            .load(item.imageUrl)
            .into(image)
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

}