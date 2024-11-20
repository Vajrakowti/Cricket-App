package com.example.spoetz

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spoetz.activites.CricketBall
import com.example.spoetz.activites.CricketBat
import com.example.spoetz.activites.CricketKit
import com.example.spoetz.adapter.Adapter
import com.example.spoetz.model.ExploreList
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class ExploreActivity : AppCompatActivity(), Adapter.MyClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemList: ArrayList<ExploreList>
    private lateinit var adapter: Adapter

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_explore)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.search)

        itemList = ArrayList()
        itemList.add(ExploreList(R.drawable.kit,"CRICKET KIT"))
        itemList.add(ExploreList(R.drawable.cbat,"CRICKET BAT"))
        itemList.add(ExploreList(R.drawable.ball3,"CRICKET BALL"))
        itemList.add(ExploreList(R.drawable.wicket,"WICKETS"))
        itemList.add(ExploreList(R.drawable.gloves,"GLOVES"))
        itemList.add(ExploreList(R.drawable.pads,"PADS"))
        itemList.add(ExploreList(R.drawable.helment,"HELMET"))
        itemList.add(ExploreList(R.drawable.bail,"BAILS"))
        itemList.add(ExploreList(R.drawable.chest,"CHEST GUARD"))
        itemList.add(ExploreList(R.drawable.shoes,"SHOES"))
        itemList.add(ExploreList(R.drawable.jersey,"JERSEY"))

        adapter = Adapter(itemList, this@ExploreActivity)

        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)


        // Implement search functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })


        // Bottom Navigation

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.bottom_search

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    // Slide left to right for going back to Home
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right)
                    finish()
                    true
                }
                R.id.bottom_search -> true
                R.id.bottom_cart -> {
                    startActivity(Intent(applicationContext, CartActivity::class.java))
                    // Slide right to left for going to Cart
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
                    finish()
                    true
                }
                R.id.bottom_profile -> {
                    startActivity(Intent(applicationContext, ProfileActivity::class.java))
                    // Slide right to left for going to Profile
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
                    finish()
                    true
                }
                else -> false
            }
        }
    }



    override fun onClick(position: Int) {
        when(position){
            0 -> startActivity(Intent(this, CricketKit::class.java))        //kit
            1 -> startActivity(Intent(this, CricketBat::class.java))       //bat
            2 -> startActivity(Intent(this, CricketBall::class.java))        //ball


        }
    }


    // Function to filter list based on search query
    private fun filterList(query: String?) {
        val filteredMovies = ArrayList<ExploreList>()
        if (!query.isNullOrEmpty()) {
            for (exploreList in itemList) {
                if (exploreList.name.uppercase(Locale.getDefault())
                        .contains(query.uppercase(Locale.getDefault()))
                ) {
                    filteredMovies.add(exploreList)
                }
            }
        } else {
            filteredMovies.addAll(itemList)
        }
        adapter.filterList(filteredMovies)
    }
}