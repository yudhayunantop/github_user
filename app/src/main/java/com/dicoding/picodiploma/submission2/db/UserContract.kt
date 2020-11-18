package com.dicoding.picodiploma.submission2.db

import android.provider.BaseColumns

internal class UserContract {

    internal class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val USERNAME = "username"
            const val AVATAR = "avatar"
        }

    }
}