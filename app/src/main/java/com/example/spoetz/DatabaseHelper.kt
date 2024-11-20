package com.example.spoetz

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory
import com.example.spoetz.model.CartItem

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "carts.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_CART = "cart"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_IMAGE_URL = "image_url"
        private const val COLUMN_PRICE = "price"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_CART (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_IMAGE_URL TEXT,
                $COLUMN_PRICE TEXT
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_CART"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    // Insert item into the cart table
    fun addItemToCart(title: String,  imageBytes: ByteArray,price: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, title)
        values.put(COLUMN_IMAGE_URL, imageBytes) // Store image URL
        values.put(COLUMN_PRICE, price)
        // Insert into cart table
        db.insert(TABLE_CART, null, values)
        db.close()
    }

    // Fetch all items from the cart table
    @SuppressLint("Range")
    fun getAllItems(): List<CartItem> {
        val itemList = mutableListOf<CartItem>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_CART"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                val imageBytes = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE_URL)) // Get image as byte array
                val price = cursor.getString(cursor.getColumnIndex(COLUMN_PRICE))

                // Add CartItem with ByteArray to the list
                itemList.add(CartItem(id, title, imageBytes,price))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return itemList
    }


    fun deleteItemFromCart(itemId: Int): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_CART, "$COLUMN_ID=?", arrayOf(itemId.toString()))
        db.close()
        return result > 0
    }

    // Optionally, add methods to remove items or handle other DB operations as needed.
}