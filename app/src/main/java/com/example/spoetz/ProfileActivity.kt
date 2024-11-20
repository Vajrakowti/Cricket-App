package com.example.spoetz

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var pickImage: Button
    private lateinit var saveImage: Button

    private lateinit var getImage : ActivityResultLauncher<String>
    private var imageUri : Uri? = null
    private var selectedImage : Bitmap? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)


        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "N/A")
        val email = sharedPreferences.getString("email", "N/A")
        val gender = sharedPreferences.getString("gender", "N/A")
        val dob = sharedPreferences.getString("dob", "N/A")

        findViewById<TextView>(R.id.titleName).text = username
        findViewById<TextView>(R.id.emailTextView).text = email
        findViewById<TextView>(R.id.genderTextView).text = gender
        findViewById<TextView>(R.id.dobTextView).text = dob


        // Pick Image

        imageView = findViewById(R.id.profileImg)
        pickImage = findViewById(R.id.titleUsername)
        saveImage = findViewById(R.id.Save)

        val db = ImageDBHelper(this)
        val im = ImageRepo(db)

        getImage = registerForActivityResult(ActivityResultContracts.GetContent(),{
            if(it!=null)
            {
                imageUri = it
            }
            imageView.setImageURI(imageUri)
        })

        pickImage.setOnClickListener {
            getImage.launch("image/*")
        }

        saveImage.setOnClickListener {
            if(imageUri!=null)
            {
                selectedImage = if(Build.VERSION.SDK_INT<28)
                {
                    MediaStore.Images.Media.getBitmap(this.contentResolver,imageUri)
                }
                else
                {
                    val source = ImageDecoder.createSource(this.contentResolver, imageUri!!)
                    ImageDecoder.decodeBitmap(source)
                }

                selectedImage?.let {
                    val id = im.insertImage(it)
                    Toast.makeText(this,"Image saved with id : $id", Toast.LENGTH_LONG).show()
                }?: Toast.makeText(this,"Please select an image!", Toast.LENGTH_LONG).show()
            }
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.bottom_profile

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
                R.id.bottom_cart -> {
                    startActivity(Intent(applicationContext, CartActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right)
                    finish()
                    true
                }
                R.id.bottom_profile -> true
                else -> false
            }
        }
    }
}