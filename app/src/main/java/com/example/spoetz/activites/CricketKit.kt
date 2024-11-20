package com.example.spoetz.activites

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spoetz.DetailActivity
import com.example.spoetz.R
import com.example.spoetz.adapter.CricketBallAdapter
import com.example.spoetz.adapter.CricketBatAdapter
import com.example.spoetz.adapter.CricketKitAdapter
import com.example.spoetz.model.Bat
import java.util.Locale

class CricketKit : AppCompatActivity(),CricketKitAdapter.MyClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var kitList: ArrayList<Bat>
    private lateinit var adapter: CricketKitAdapter


    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cricket_kit)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.search)


        kitList = ArrayList()
        kitList.add(Bat(R.drawable.ckit,"CW Championship Cricket Kit for Men Full Size Cricket Kit Set English Willow Cricket Kit Full Cricket Kit Bag Full Set Cricket Kit Full Size Adult Set Senior Kit- Red Black White",16))
        kitList.add(Bat(R.drawable.ckit2,"SG Multicolor Economy Cricket Set Size- 6 with Helmet",17))
        kitList.add(Bat(R.drawable.ckit3,"DSC Belter Kashmir Willow Cricket Kit with Helmet, Size-Mens, Left Hand",18))
        kitList.add(Bat(R.drawable.ckit4,"SG Full Cricket Kit for (Senior) + Legguard + Batting Gloves + Kitbag + Thigh Guard + Arm Guard + Abdo Guard A Complete Economy Cricket kit for Batsmen with Wooden Wicket Stumps",24))
        kitList.add(Bat(R.drawable.ckit5,"Klapp Fighter Cricket Kit Set with Kashmir Willow Cricket Bat (6, Multicolour)",19))
        kitList.add(Bat(R.drawable.ckit6,"CW Bullet Green Right Hand Cricket Kit Complete Cricket Set with Kashmir Willow Bat & Leather Ball Junior - Youth & Boys Backpack Kit Complete Kit (Size 6 for 12-13 Yr)",20))
        kitList.add(Bat(R.drawable.ckit7,"CW Player Choice Cricket Kit Without Bat Cricket Duffle Backpack Bag Kashmir Willow Cricket Kit for Boys Cricket Full Kit for Men Full Size Cricket Bat Cricket Kits All Age",16))
        kitList.add(Bat(R.drawable.ckit8,"CW Trainer Red Blue Wooden Cricket kit for All Age Groups Kashmir Willow Cricket Bat Size 3-6 Cricket Glove Kit for Boys - Senior Men Full Size Left & Right Hand Kit (3 for 5-7 Yr, Right Blue)",17))
        kitList.add(Bat(R.drawable.ckit9,"Whitedot Falcon Kashmir Willow Cricket Combo Kit Set, Suitable for Boys/Small, Left Hand",18))
        kitList.add(Bat(R.drawable.ckit10,"Klapp Personal Complete Cricket Kit Combo with Spotlight Cricket Ball (BOY)",19))


        adapter = CricketKitAdapter(kitList, this@CricketKit)

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

    }

    override fun onClick(position: Int) {
        // Retrieve the selected movie from the movieList based on the clicked position
        val selectedMovie = kitList[position]

        // Create an intent to navigate to DetailActivity
        val intent = Intent(this, DetailActivity::class.java)

        // Pass the correct movie ID to the DetailActivity
        intent.putExtra("MOVIE_ID", selectedMovie.id)

        // Start the DetailActivity with the intent
        startActivity(intent)

    }

    // Function to filter list based on search query
    private fun filterList(query: String?) {
        val filteredMovies = ArrayList<Bat>()
        if (!query.isNullOrEmpty()) {
            for (exploreList in kitList) {
                if (exploreList.name.uppercase(Locale.getDefault())
                        .contains(query.uppercase(Locale.getDefault()))
                ) {
                    filteredMovies.add(exploreList)
                }
            }
        } else {
            filteredMovies.addAll(kitList)
        }
        adapter.filterList(filteredMovies)
    }


}