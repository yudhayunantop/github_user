package com.dicoding.picodiploma.submission2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel : ViewModel(){

    val listUsers = MutableLiveData<ArrayList<User>>()
    var detailUsers = MutableLiveData<ArrayList<User>>()
    val following = MutableLiveData<ArrayList<User>>()
    val followers = MutableLiveData<ArrayList<User>>()

    fun setUser(username: String) {
        val listUser = ArrayList<User>()

        val apiKey = "924a34b35c59afc57009f514c5fd6de5fe706750"
        val url = "https://api.github.com/search/users?q=$username"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    //parsing json
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)

                    val items = responseObject.getJSONArray("items")
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")

                        val user = User()
                        user.username = username
                        user.avatar = avatar
                        listUser.add(user)
                    }
                    //set data ke adapter
                    listUsers.postValue(listUser)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getUser(): LiveData<ArrayList<User>> {
        return listUsers
    }

    fun setDetail(username: String) {
        val detailUser = ArrayList<User>()

        val apiKey = "924a34b35c59afc57009f514c5fd6de5fe706750"
        val url = "https://api.github.com/users/$username"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {

                    //parsing json
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)

                        val name = responseObject.getString("name")
                        val username = responseObject.getString("login")
                        val location = responseObject.getString("location")
                        val company = responseObject.getString("company")
                        val repository = responseObject.getString("public_repos")
                        val avatar = responseObject.getString("avatar_url")

                        val user = User()
                        user.avatar = avatar
                        user.username = username
                        user.name = name
                        user.location = location
                        user.company = company
                        user.repository = repository

                        detailUser.add(user)

                    detailUsers.postValue(detailUser)
                }

                catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getDetail(): LiveData<ArrayList<User>>{
        return detailUsers
    }

    fun setFollowers(username: String) {
        val listFollowers = ArrayList<User>()

        val apiKey = "924a34b35c59afc57009f514c5fd6de5fe706750"
        val url = "https://api.github.com/users/$username/followers"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONArray(result)

                    for (i in 0 until responseObject.length()) {
                        val follower = responseObject.getJSONObject(i)
                        val user = User()
                        user.username = follower.getString("login")
                        user.avatar = follower.getString("avatar_url")
                        listFollowers.add(user)
                        Log.d("FOLLOWER", url)
                    }
                    followers.postValue(listFollowers)
                }
                catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getFollowers(): LiveData<ArrayList<User>>{
        return followers
    }

    fun setFollowing(username: String) {
        val listFollowing = ArrayList<User>()

        val apiKey = "924a34b35c59afc57009f514c5fd6de5fe706750"
        val url = "https://api.github.com/users/$username/following"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONArray(result)

                    for (i in 0 until responseObject.length()) {
                        val follower = responseObject.getJSONObject(i)
                        val user = User()
                        user.username = follower.getString("login")
                        user.avatar = follower.getString("avatar_url")
                        listFollowing.add(user)
                        Log.d("FOLLOwing", url)
                    }
                    following.postValue(listFollowing)
                }
                catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getFollowing(): LiveData<ArrayList<User>>{
        return following
    }

}
