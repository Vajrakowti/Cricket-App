package com.example.spoetz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spoetz.R
import com.example.spoetz.activites.CricketBall
import com.example.spoetz.activites.CricketKit
import com.example.spoetz.model.Bat

class CricketKitAdapter(val kitList: ArrayList<Bat>, val listener: CricketKit): RecyclerView.Adapter<CricketKitAdapter.MyViewHolder>() {

    // Define a filtered list for search
    private var filteredList = ArrayList<Bat>().apply { addAll(kitList) }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CricketKitAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bat_item,parent,false))
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: CricketKitAdapter.MyViewHolder, position: Int) {
        val KitMovie = filteredList[position]
        holder.textView.text = KitMovie.name                               // names given in Movie
        holder.imageView.setImageResource(KitMovie.image)
    }

    interface MyClickListener {
        fun onClick(position: Int)
    }
}