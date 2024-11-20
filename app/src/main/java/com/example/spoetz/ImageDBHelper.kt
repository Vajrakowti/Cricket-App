package com.example.spoetz

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ImageDBHelper(c : Context) : SQLiteOpenHelper(c, DATABASE_NAME,null, DATABASE_VERSION) {
    companion object
    {
        const val DATABASE_NAME = "ImageDatabase"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Images"
        const val COLUMN_ID = "id"
        const val COLUMN_IMAGE = "image"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE $TABLE_NAME(${COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,$COLUMN_IMAGE BLOB)")
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val q = ("DROP TABLE IF EXISTS $TABLE_NAME")
        db?.execSQL(q)
        onCreate(db)
    }
}