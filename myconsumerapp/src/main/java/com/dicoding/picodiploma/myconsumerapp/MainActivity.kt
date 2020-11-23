package com.dicoding.picodiploma.myconsumerapp

import android.database.ContentObserver
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.myconsumerapp.adapter.ListUserAdapter
import com.dicoding.picodiploma.myconsumerapp.db.UserContract
import com.dicoding.picodiploma.myconsumerapp.db.UserContract.FavoriteColumns.Companion.CONTENT_URI
import kotlinx.android.synthetic.main.activity_main.*

private lateinit var adapter: ListUserAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_fav.setHasFixedSize(true)

        supportActionBar?.title = "Favorite"

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        rv_fav.layoutManager = LinearLayoutManager(this)
        rv_fav.adapter = adapter

        supportActionBar?.title = "Favorite"

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler){

        }
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
        var data = cursor?.let { mapCursortoArrayList(it) }
        adapter.setData(data!!)

    }

    fun mapCursortoArrayList(favoriteUser: Cursor): ArrayList<User>{
        val userList = ArrayList<User>()
        while (favoriteUser.moveToNext()){
            val avatar = favoriteUser.getString(favoriteUser.getColumnIndexOrThrow((UserContract.FavoriteColumns.AVATAR)))
            val username = favoriteUser.getString(favoriteUser.getColumnIndexOrThrow((UserContract.FavoriteColumns.USERNAME)))
            userList.add(User(avatar, username))
        }
        return userList
    }

}