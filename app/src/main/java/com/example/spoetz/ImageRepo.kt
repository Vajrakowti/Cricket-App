package com.example.spoetz

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class ImageRepo (val dbHelper : ImageDBHelper) {

    fun bitmapToByteArray(bitmap: Bitmap) : ByteArray
    {
        val stream = ByteArrayOutputStream()   // ByteArrayOutputStream() creates a stream to hold the byte data.
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
        return stream.toByteArray()
    }

    fun byteArrayToBitmap(byteArray: ByteArray) : Bitmap
    {
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }

    fun insertImage(bitmap: Bitmap) : Long
    {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val cv = ContentValues()
        cv.put(ImageDBHelper.COLUMN_IMAGE,bitmapToByteArray(bitmap))
        return db.insert(ImageDBHelper.TABLE_NAME,null,cv)
    }

}