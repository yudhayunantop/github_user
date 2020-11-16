package com.dicoding.picodiploma.submission2.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dicoding.picodiploma.submission2.db.UserContract.FavoriteColumns.Companion.TABLE_NAME

internal class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {

    companion object {
        private const val DATABASE_NAME = "dbfavuser"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_FAV = "CREATE TABLE $TABLE_NAME" +
                " (${UserContract.FavoriteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${UserContract.FavoriteColumns.USERNAME} TEXT NOT NULL," +
                " ${UserContract.FavoriteColumns.AVATAR} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAV)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}