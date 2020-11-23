package com.dicoding.picodiploma.submission2.db

import android.net.Uri
import android.provider.BaseColumns

object UserContract {

    const val AUTHORITY = "com.dicoding.picodiploma.submission2"
    const val SCHEME = "content"

    class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val USERNAME = "username"
            const val AVATAR = "avatar"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
}