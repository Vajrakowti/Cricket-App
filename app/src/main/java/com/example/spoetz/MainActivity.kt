package com.example.spoetz

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.spoetz.activites.CricketBall
import com.example.spoetz.activites.CricketBat
import com.example.spoetz.adapter.BrandAdapter
import com.example.spoetz.model.Brand
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var image1 : ImageView
    private lateinit var image2 : ImageView
    private lateinit var image3 : ImageView
    private lateinit var image4 : ImageView
    private lateinit var image5 : ImageView
    private lateinit var image6 : ImageView

    private lateinit var viewpager : ViewPager2

    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    private lateinit var batClick : ImageView
    private lateinit var ballClick : ImageView
    private lateinit var exploreClick : ImageView

    private lateinit var recyclerView: RecyclerView
    private lateinit var brandList: ArrayList<Brand>
    private lateinit var adapters: BrandAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        viewpager = findViewById(R.id.viewpager)
        image1    = findViewById(R.id.iv1)
        image2    = findViewById(R.id.iv2)
        image3    = findViewById(R.id.iv3)
        image4    = findViewById(R.id.iv4)
        image5    = findViewById(R.id.iv5)
        image6    = findViewById(R.id.iv6)

        val images = listOf(R.drawable.banner1,R.drawable.banner2,R.drawable.banner3,R.drawable.banner4,R.drawable.banner5)
        val adapter = ViewPagerAdapter(images)               // Here passing images to adapter
        viewpager.adapter = adapter                          // Here we setting adapter on viewpager


        batClick = findViewById(R.id.batImage)

        batClick.setOnClickListener {
            val intent = Intent(this@MainActivity, CricketBat::class.java)
            startActivity(intent)
        }

        ballClick = findViewById(R.id.ballImage)

        ballClick.setOnClickListener {
            val intent = Intent(this@MainActivity,CricketBall::class.java)
            startActivity(intent)
        }

        exploreClick = findViewById(R.id.explore)

        exploreClick.setOnClickListener {
            val intent = Intent(this@MainActivity,ExploreActivity::class.java)
            startActivity(intent)
        }



        // Dot Indicator and page change callback
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                changeColor()
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                changeColor()
            }
        })

        // Auto-slide logic
        startAutoSlide()



        // Bottom Navigation

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.bottom_home

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> true
                R.id.bottom_search -> {
                    startActivity(Intent(applicationContext, ExploreActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
                    finish()
                    true
                }
                R.id.bottom_cart -> {
                    startActivity(Intent(applicationContext, CartActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
                    finish()
                    true
                }
                R.id.bottom_profile -> {
                    startActivity(Intent(applicationContext, ProfileActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
                    finish()
                    true
                }
                else -> false
            }
        }


        // Brand

        recyclerView = findViewById(R.id.recyclerView3)

        brandList = ArrayList()
        brandList.add(Brand(R.drawable.koko2))
        brandList.add(Brand(R.drawable.grey))
        brandList.add(Brand(R.drawable.ceat))
        brandList.add(Brand(R.drawable.spartan))
        brandList.add(Brand(R.drawable.puma2))
        brandList.add(Brand(R.drawable.mrf))
        brandList.add(Brand(R.drawable.nike))
        brandList.add(Brand(R.drawable.dsc))

        adapters = BrandAdapter(brandList)

        recyclerView.adapter = adapters
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


    private fun startAutoSlide() {
        runnable = object : Runnable {
            override fun run() {
                val nextItem = (viewpager.currentItem + 1) % viewpager.adapter!!.itemCount
                viewpager.setCurrentItem(nextItem, true)
                handler.postDelayed(this, 2000) // Slide every 2 seconds
            }
        }
        handler.postDelayed(runnable!!, 2000)
    }

    private fun stopAutoSlide() {
        handler.removeCallbacks(runnable!!)
    }

    private fun changeColor(){

        when(viewpager.currentItem){
            0->{
                image1.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                image2.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image3.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image4.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image5.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image6.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
            }
            1->{
                image1.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image2.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                image3.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image4.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image5.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image6.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
            }
            2->{
                image1.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image2.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image3.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                image4.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image5.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image6.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
            }
            3->{
                image1.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image2.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image3.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image4.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                image5.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image6.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
            }
            4->{
                image1.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image2.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image3.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image4.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image5.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
                image6.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
            }
            5->{
                image1.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image2.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image3.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image4.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image5.setBackgroundColor(applicationContext.resources.getColor(R.color.lightmaincolour))
                image6.setBackgroundColor(applicationContext.resources.getColor(R.color.white))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAutoSlide()
    }


}