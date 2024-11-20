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
import com.example.spoetz.model.Bat
import java.util.Locale

class CricketBall : AppCompatActivity(),CricketBallAdapter.MyClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ballList: ArrayList<Bat>
    private lateinit var adapter: CricketBallAdapter

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cricket_ball)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.search)


        ballList = ArrayList()
        ballList.add(Bat(R.drawable.cball3,"AnNafi White Cricket Rubber Soft Balls for Practice A Grade Handstitched | Senior Official Hand Stitched No Stamp for Indoor and Outdoor Practice Cricket Balls",11))
        ballList.add(Bat(R.drawable.ball55,"Jaspo T-20 Soft Pvc Cricket Balls Red (Pack Of Three) Standard Size - Pvc",12))
        ballList.add(Bat(R.drawable.cball4,"Rubber Green Tennis Cricket Ball | Pack Of 3 Balls",13))
        ballList.add(Bat(R.drawable.cball5,"TIMA Leather Cricket Shot Practice Hanging Ball, String Cricket Ball and Knocking Cricket Ball with Rope (Multi-Color) (Pack of 1)",14))
        ballList.add(Bat(R.drawable.cball6,"FACTO POWER Soft Cricket Solid, Light Weight, Tennis Balls Practice, Recommended for Indoor/Outdoor Street & Beach Cricket (Pack of One) (Color : Green)(Model:Spiro) - Resin, Rubber, Standard",15))
        ballList.add(Bat(R.drawable.cball7,"TIMA Sports Fun Poly Hard Synthetic Red Cricket Ball (Red Pack of 12)",11))
        ballList.add(Bat(R.drawable.cball8,"TIMA Synthetic Cricket Ball Poly Hard Cricket Balls - Indoor & Outdoor Training Cricket Ball for Coaching Practice (Blue Pack of 3)",12))
        ballList.add(Bat(R.drawable.cball9,"Sixit Lite Cricket Tennis Ball - Pack of 6 , Green , Material : Rubber , Standard Size",13))
        ballList.add(Bat(R.drawable.cball3,"Rubber Green Tennis Cricket Ball | Pack Of 3 Balls",14))
        ballList.add(Bat(R.drawable.cball5,"TIMA Sports Fun Poly Hard Synthetic Red Cricket Ball (Red Pack of 12)",15))


        adapter = CricketBallAdapter(ballList, this@CricketBall)

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
        val selectedMovie = ballList[position]

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
            for (exploreList in ballList) {
                if (exploreList.name.uppercase(Locale.getDefault())
                        .contains(query.uppercase(Locale.getDefault()))
                ) {
                    filteredMovies.add(exploreList)
                }
            }
        } else {
            filteredMovies.addAll(ballList)
        }
        adapter.filterList(filteredMovies)
    }


}