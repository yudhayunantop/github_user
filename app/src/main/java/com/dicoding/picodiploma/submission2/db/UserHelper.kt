package com.dicoding.picodiploma.submission2.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.dicoding.picodiploma.submission2.db.UserContract.FavoriteColumns.Companion.TABLE_NAME
import com.dicoding.picodiploma.submission2.db.UserContract.FavoriteColumns.Companion.USERNAME
import java.sql.SQLException

class UserHelper (context: Context){

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase


    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: UserHelper? = null

        fun getInstance(context: Context): UserHelper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: UserHelper(context)
                }
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                "$USERNAME ASC")
    }

    fun queryById(id: String): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                "$USERNAME = ?",
                arrayOf(id),
                null,
                null,
                null,
                null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$id'", null)
    }
}