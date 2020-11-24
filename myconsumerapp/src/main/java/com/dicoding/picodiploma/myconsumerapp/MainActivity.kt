package com.dicoding.picodiploma.myconsumerapp

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.myconsumerapp.adapter.ListUserAdapter
import com.dicoding.picodiploma.myconsumerapp.db.UserContract.FavoriteColumns.Companion.CONTENT_URI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
            override fun onChange(self: Boolean) {
                loadData()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
        loadData()


    }

    fun loadData() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredUsers = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                ConvertHelper.mapCursortoArrayList(cursor!!)

            }
            val pengguna = deferredUsers.await()
            adapter.setData(pengguna)

        }
    }


}