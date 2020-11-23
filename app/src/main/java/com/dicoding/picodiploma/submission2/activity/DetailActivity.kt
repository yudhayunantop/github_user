package com.dicoding.picodiploma.submission2.activity

import android.content.ContentValues
import android.database.ContentObserver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.submission2.MainViewModel
import com.dicoding.picodiploma.submission2.R
import com.dicoding.picodiploma.submission2.adapter.SectionsPagerAdapter
import com.dicoding.picodiploma.submission2.User
import com.dicoding.picodiploma.submission2.db.UserContract
import com.dicoding.picodiploma.submission2.db.UserContract.FavoriteColumns.Companion.AVATAR
import com.dicoding.picodiploma.submission2.db.UserContract.FavoriteColumns.Companion.CONTENT_URI
import com.dicoding.picodiploma.submission2.db.UserContract.FavoriteColumns.Companion.USERNAME
import com.dicoding.picodiploma.submission2.db.UserHelper
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    //private lateinit var userHelper: UserHelper
    private lateinit var uriWithId: Uri


    companion object{
        const val EXTRA_USER = "extra_user"
        const val EXTRA_AVATAR = "extra_avatar"

        const val RESULT_ADD = 101
        const val RESULT_DELETE = 301
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

//        userHelper = UserHelper.getInstance(applicationContext)
//        userHelper.open()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler){

        }
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)


        val dataUser = intent.getParcelableExtra<User>(EXTRA_USER) as User
        val username = dataUser.username
        val avatar = dataUser.avatar

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        username?.let{mainViewModel.setDetail(it)}
        mainViewModel.getDetail().observe(this, Observer { detailUser ->
                if (detailUser != null) {
                    showLoading(true)
                    Glide.with(this)
                            .load(detailUser.get(0).avatar)
                            .apply(RequestOptions().override(133, 133))
                            .into(tv_img_avatar)
                    tv_name.text = detailUser.get(0).name
                    tv_username.text = detailUser.get(0).username
                    tv_company.text = detailUser.get(0).company
                    tv_location.text = detailUser.get(0).location
                    tv_repository.text = detailUser.get(0).repository
                    showLoading(false)
                }
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.username = dataUser.username
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = dataUser.username


        // Favorit
        //var pengecekan = username?.let { userHelper.queryById(it) }

        var pengecekan = username?.let { contentResolver.query(CONTENT_URI, null, null, null, null) }

        if (pengecekan!!.position > 0) {
                var statusFavorite = true
                setStatusFavorite(statusFavorite)
                floatingActionButton.setOnClickListener {

                    //kode delete db
                    username?.let { it -> contentResolver.delete(CONTENT_URI, null, null)}

                    setResult(RESULT_DELETE, intent)

                    statusFavorite = false
                    setStatusFavorite(statusFavorite)

                    finish()
                }
            }
            else {
                var statusFavorite = false
                setStatusFavorite(statusFavorite)
                floatingActionButton.setOnClickListener {
                    statusFavorite = !statusFavorite

                    //kode insert db
                    val values = ContentValues()

                    values.put(AVATAR, avatar)
                    values.put(USERNAME, username)
                    contentResolver.insert(CONTENT_URI,values)

                    setResult(RESULT_ADD, intent)

                    statusFavorite = true
                    setStatusFavorite(statusFavorite)

                    finish()
                }
            }

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar2.visibility = View.VISIBLE
        } else {
            progressBar2.visibility = View.GONE
        }
    }

    private fun setStatusFavorite(statusFavorite:Boolean) {
        if (statusFavorite){
            //ganti src icon ke favorite
            floatingActionButton.setImageResource(R.drawable.favoriteblack)
        }
        else{
            //ganti src icon ke not favorite
            floatingActionButton.setImageResource(R.drawable.favoritewhite)
        }
    }


}

