package com.example.spoetz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spoetz.R
import com.example.spoetz.model.ExploreList

class Adapter (val itemList: ArrayList<ExploreList>, val listener: MyClickListener): RecyclerView.Adapter<Adapter.MyViewHolder>() {


    // Define a filtered list for search
    private var filteredList = ArrayList<ExploreList>().apply { addAll(itemList) }

    // Add method to filter the list based on search query
    fun filterList(filteredMovies: ArrayList<ExploreList>) {
        filteredList = filteredMovies
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = filteredList[position]
        holder.textView.text = movie.name
        holder.imageView.setImageResource(movie.image)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    interface MyClickListener {
        fun onClick(position: Int)
    }
}