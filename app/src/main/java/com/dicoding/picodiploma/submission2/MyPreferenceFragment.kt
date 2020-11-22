package com.dicoding.picodiploma.submission2

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference

class MyPreferenceFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var AKTIFASI: String
    private lateinit var aktifasiPreference: SwitchPreference

    @SuppressLint("ResourceType")
    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.layout.preferences)

        init()
        setSummaries()

    }

    private fun init() {
        AKTIFASI = resources.getString(R.string.key_aktifasi)
        aktifasiPreference = findPreference<SwitchPreference> (AKTIFASI) as SwitchPreference
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == AKTIFASI) {
            aktifasiPreference.isChecked = sharedPreferences!!.getBoolean(AKTIFASI, false)
        }
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        aktifasiPreference.isChecked = sh.getBoolean(AKTIFASI, false)
    }
}