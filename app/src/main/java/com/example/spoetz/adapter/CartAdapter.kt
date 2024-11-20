package com.example.spoetz.adapter

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spoetz.DatabaseHelper
import com.example.spoetz.PaymentMode
import com.example.spoetz.R
import com.example.spoetz.model.CartItem

class CartAdapter(private val cartItems: MutableList<CartItem>,private val dbHelper: DatabaseHelper ) : RecyclerView.Adapter<CartAdapter.CartViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]

        holder.title.text = cartItem.title
        holder.price.text = cartItem.price

        // Ensure the image exists and convert the byte array to Bitmap
        val bitmap = if (cartItem.image.isNotEmpty()) {
            BitmapFactory.decodeByteArray(cartItem.image, 0, cartItem.image.size)
        } else {
            null
        }

        // Only set image if it's not null
        if (bitmap != null) {
            holder.image.setImageBitmap(bitmap)
        }

        holder.deleteButton.setOnClickListener {
            val itemId = cartItem.id

            // Remove item from database
            dbHelper.deleteItemFromCart(itemId)

            // Remove item from list and notify adapter
            cartItems.removeAt(position)
            notifyItemRemoved(position)
        }

        // Buy Now button click listener
        holder.buyNowButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, PaymentMode::class.java)
            intent.putExtra("item_id", cartItem.id) // Pass item ID or any other details if needed
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int = cartItems.size

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.cartItemTitle)
        val image: ImageView = view.findViewById(R.id.cartItemImage)
        val price: TextView = view.findViewById(R.id.cartItemPrice)
        val deleteButton: ImageView = view.findViewById(R.id.deleteItemButton)
        val buyNowButton: Button = view.findViewById(R.id.cartBuyNow)

    }
}