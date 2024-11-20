package com.example.spoetz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spoetz.R
import com.example.spoetz.activites.CricketBall
import com.example.spoetz.model.Bat

class CricketBallAdapter(val ballList: ArrayList<Bat>, val listener: CricketBall): RecyclerView.Adapter<CricketBallAdapter.MyViewHolder>() {

    // Define a filtered list for search
    private var filteredList = ArrayList<Bat>().apply { addAll(ballList) }

    // Add method to filter the list based on search query
    fun filterList(filteredMovies: ArrayList<Bat>) {
        filteredList = filteredMovies
        notifyDataSetChanged()
    }


    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val imageView : ImageView = itemView.findViewById(R.id.batimageView)
        val textView  : TextView =  itemView.findViewById(R.id.battextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                listener.onClick(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CricketBallAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bat_item,parent,false))
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: CricketBallAdapter.MyViewHolder, position: Int) {
        val BallMovie = filteredList[position]
        holder.textView.text = BallMovie.name                               // names given in Movie
        holder.imageView.setImageResource(BallMovie.image)
    }


    interface MyClickListener {
        fun onClick(position: Int)
    }

}