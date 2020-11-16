package com.dicoding.picodiploma.submission2.fragment

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
import com.dicoding.picodiploma.submission2.adapter.ListUserAdapter
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {

    companion object {

        private  val ARG_USERNAME = "username"

        fun newInstance(username: String): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments=bundle
            return fragment
        }

    }

    private lateinit var adapter: ListUserAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_followers.setHasFixedSize(true)

        val username = arguments?.getString(ARG_USERNAME)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        rv_followers.layoutManager = LinearLayoutManager(activity)
        rv_followers.adapter = adapter

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        mainViewModel.setFollowers(username!!)
        showLoading(true)

        activity?.let {
            mainViewModel.getFollowers().observe(it, Observer { listFollowers ->
                if (listFollowers != null) {
                    adapter.setData(listFollowers)
                    showLoading(false)
                }
            })
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar3.visibility = View.VISIBLE
        } else {
            progressBar3.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }


}