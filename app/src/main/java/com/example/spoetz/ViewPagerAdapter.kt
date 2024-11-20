package com.example.spoetz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ViewPagerAdapter(private val images:List<Int>): RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {
    inner class ViewPagerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val imageView: ImageView =itemView.findViewById(R.id.imageviewpager)
    }


    // Here we giving path of imagevieewpager.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.ViewPagerViewHolder {

        return ViewPagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.imageviewpager,parent,false))

    }

    // Here we declaring image antey telling we will enter image.

    override fun onBindViewHolder(holder: ViewPagerAdapter.ViewPagerViewHolder, position: Int) {
        val curimage = images[position]
        holder.imageView.setImageResource(curimage)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}