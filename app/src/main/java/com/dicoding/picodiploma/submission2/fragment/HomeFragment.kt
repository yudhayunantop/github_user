package com.dicoding.picodiploma.submission2.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.submission2.MainViewModel
import com.dicoding.picodiploma.submission2.R
import com.dicoding.picodiploma.submission2.User
import com.dicoding.picodiploma.submission2.activity.DetailActivity
import com.dicoding.picodiploma.submission2.adapter.ListUserAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var adapter: ListUserAdapter
    private lateinit var mainViewModel: MainViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_users.setHasFixedSize(true)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        rv_users.layoutManager = LinearLayoutManager(activity)
        rv_users.adapter = adapter

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)


        btnUsername.setOnClickListener {
            val username = editUsername.text.toString()
            if (username.isEmpty()) return@setOnClickListener
            showLoading(true)

            mainViewModel.setUser(username)
        }

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                val moveIntent = Intent(activity, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.EXTRA_USER, data)
                moveIntent.putExtra(DetailActivity.EXTRA_AVATAR, data)
                startActivity(moveIntent)
            }
        })

        activity?.let {
            mainViewModel.getUser().observe(it, Observer { listUser ->
                if (listUser != null) {
                    adapter.setData(listUser)
                    showLoading(false)
                }
            })
        }

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}