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
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    companion object {

            private  val ARG_USERNAME = "username"

            fun newInstance(username: String): FollowingFragment {
                val fragment = FollowingFragment()
                val bundle = Bundle()
                bundle.putString(ARG_USERNAME, username)
                fragment.arguments = bundle
                return fragment
            }

    }

    private lateinit var adapter: ListUserAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_following.setHasFixedSize(true)

        val username = arguments?.getString(FollowingFragment.ARG_USERNAME)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        rv_following.layoutManager = LinearLayoutManager(activity)
        rv_following.adapter = adapter

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        mainViewModel.setFollowing(username!!)
        showLoading(true)

        activity?.let {
            mainViewModel.getFollowing().observe(it, Observer { listFollowing ->
                if (listFollowing != null) {
                    adapter.setData(listFollowing)
                    showLoading(false)
                }
            })
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar4.visibility = View.VISIBLE
        } else {
            progressBar4.visibility = View.GONE
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }
}