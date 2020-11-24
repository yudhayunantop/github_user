package com.dicoding.picodiploma.submission2

import android.database.Cursor
import com.dicoding.picodiploma.submission2.db.UserContract

object ConvertHelper {
    fun mapCursortoArrayList(favoriteUser: Cursor): ArrayList<User> {
        val userList = ArrayList<User>()
        while (favoriteUser.moveToNext()) {
            val avatar = favoriteUser.getString(favoriteUser.getColumnIndexOrThrow((UserContract.FavoriteColumns.AVATAR)))
            val username = favoriteUser.getString(favoriteUser.getColumnIndexOrThrow((UserContract.FavoriteColumns.USERNAME)))
            userList.add(User(avatar, username))
        }
        return userList
    }

}