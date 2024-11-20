package com.example.spoetz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spoetz.R
import com.example.spoetz.activites.CricketBat
import com.example.spoetz.model.Bat

class CricketBatAdapter(val batList: ArrayList<Bat>, val listener: CricketBat): RecyclerView.Adapter<CricketBatAdapter.MyViewHolder>(){

    // Define a filtered list for search
    private var filteredList = ArrayList<Bat>().apply { addAll(batList) }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bat_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val BatMovie = filteredList[position]
        holder.textView.text = BatMovie.name                               // names given in Movie
        holder.imageView.setImageResource(BatMovie.image)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    interface MyClickListener {
        fun onClick(position: Int)
    }
}