package com.example.spoetz.activites

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spoetz.DetailActivity
import com.example.spoetz.R
import com.example.spoetz.adapter.CricketBatAdapter
import com.example.spoetz.model.Bat
import java.util.Locale

class CricketBat : AppCompatActivity(),CricketBatAdapter.MyClickListener  {

    private lateinit var recyclerView: RecyclerView
    private lateinit var batList: ArrayList<Bat>
    private lateinit var adapter: CricketBatAdapter

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cricket_bat)


        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.search)

        batList = ArrayList()
        batList .add(Bat(R.drawable.cbat2,"Cricket Bat Full Size Popular Willow SG Cricket Bat with Bat Cover Play for Tennis Ball | Rubber Ball | Plastic Ball (Blue)",1))
        batList .add(Bat(R.drawable.bat2,"TOSKA Cricket Bat Full Size Popular Willow Kookaburra Cricket Bat for Tennis Ball, Rubber Ball | Plastic Ball (Men|Women) (Black)",2))
        batList .add(Bat(R.drawable.koko,"KOOKABURRA Beast 1.0 Cricket English Willow BAT",3))
        batList .add(Bat(R.drawable.cbat5,"Classic Bat Popular Willow Hard Pressed Shaped For Superb Stroke SG Cricket Bat (Red)",4))
        batList .add(Bat(R.drawable.newbala,"CW New Balance TC 740 English Willow Cricket Bat Short Handle Adult New Balance English Willow Bat with Padded Cover",5))
        batList .add(Bat(R.drawable.cbat7,"Cricket Bat Full Size Popular Willow SG Cricket Bat with Bat Cover Play for Tennis Ball | Rubber Ball | Plastic Ball (Black & White)",6))
        batList .add(Bat(R.drawable.cbat2,"STEFFER Kashmir Willow Cricket Bat Hard/Tennis Ball Bat Short Handle with for Men and Women (Style 1)",7))
        batList .add(Bat(R.drawable.bat1,"STEFFER Kashmir Willow Cricket Bat Hard/Tennis Ball Bat Short Handle with for Men and Women (Style 1)",8))
        batList .add(Bat(R.drawable.cbat3,"AENOX Special Grand Edition Bat Master Kashmir Willow Cricket Bat Suitable for Soft Tennis Ball Extra Sturdy Grip | Ready to Play | Extra Protective Fishing Tape | Red|| (6)",9))
        batList .add(Bat(R.drawable.cbat6,"GM Six6 F2 909 English Willow Cricket Bat Short Handle Mens",10))

        adapter = CricketBatAdapter(batList, this@CricketBat)

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


        // Retrieve the selected item from the movieList based on the clicked position
        val selectedMovie = batList[position]

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
            for (exploreList in batList) {
                if (exploreList.name.uppercase(Locale.getDefault())
                        .contains(query.uppercase(Locale.getDefault()))
                ) {
                    filteredMovies.add(exploreList)
                }
            }
        } else {
            filteredMovies.addAll(batList)
        }
        adapter.filterList(filteredMovies)
    }
}

