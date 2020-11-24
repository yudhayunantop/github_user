package com.dicoding.picodiploma.submission2.activity

import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.submission2.ConvertHelper
import com.dicoding.picodiploma.submission2.R
import com.dicoding.picodiploma.submission2.User
import com.dicoding.picodiploma.submission2.adapter.ListUserAdapter
import com.dicoding.picodiploma.submission2.db.UserContract
import com.dicoding.picodiploma.submission2.db.UserContract.FavoriteColumns.Companion.CONTENT_URI
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

private lateinit var adapter: ListUserAdapter

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
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

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                val moveIntent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.EXTRA_USER, data)
                moveIntent.putExtra(DetailActivity.EXTRA_AVATAR, data)
                startActivity(moveIntent)
            }
        })


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