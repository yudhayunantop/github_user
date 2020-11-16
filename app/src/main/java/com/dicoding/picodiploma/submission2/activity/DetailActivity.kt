package com.dicoding.picodiploma.submission2.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.submission2.MainViewModel
import com.dicoding.picodiploma.submission2.R
import com.dicoding.picodiploma.submission2.adapter.SectionsPagerAdapter
import com.dicoding.picodiploma.submission2.User
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_row_user.*
import kotlinx.android.synthetic.main.item_row_user.view.*

class DetailActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel

    companion object{
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val dataUser = intent.getParcelableExtra<User>(EXTRA_USER) as User
        val username = dataUser.username

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

        var statusFavorite = false
        setStatusFavorite(statusFavorite)
        floatingActionButton.setOnClickListener{
            statusFavorite = !statusFavorite
            //kode insert db
            setStatusFavorite(statusFavorite)
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
        }


        else{
            //ganti src icon ke not favorite
        }
    }


}

