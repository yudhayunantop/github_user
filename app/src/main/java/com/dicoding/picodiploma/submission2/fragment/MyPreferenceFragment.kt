package com.dicoding.picodiploma.submission2.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.picodiploma.submission2.alarm.AlarmHelper
import com.dicoding.picodiploma.submission2.R
import java.util.*

class MyPreferenceFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var AKTIFASI: String
    private lateinit var aktifasiPreference: SwitchPreference

    companion object{
        const val ALARM_ID_REPEATING = 100

    }

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)
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
                // Create reminder alarm
                context?.let {
                    AlarmHelper.createAlarm(
                        it,
                        getString(R.string.app_name),
                        "Ayo temukan pengguna lain pada Github!",
                        ALARM_ID_REPEATING,
                        Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, 9)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                        }
                    )
                }


            } else {
                // Delete reminder alarm
                context?.let { AlarmHelper.cancelAlarm(it, ALARM_ID_REPEATING) }

            }

    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        aktifasiPreference.isChecked = sh.getBoolean(AKTIFASI, false)
    }

}