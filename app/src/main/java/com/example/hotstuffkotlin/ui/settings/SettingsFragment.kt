package com.example.hotstuffkotlin.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.utils.SharedPreferenceHelper

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var preferenceHelper: SharedPreferenceHelper
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        preferenceHelper = SharedPreferenceHelper.getInstance(requireContext())
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        when (key) {
            "theme" -> {
                when (preferenceHelper.getThemePref()) {
                    getString(R.string.label_light) -> setTheme(AppCompatDelegate.MODE_NIGHT_NO)
                    getString(R.string.label_dark) -> setTheme(AppCompatDelegate.MODE_NIGHT_YES)
                    getString(R.string.label_system) -> setTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
            "currency" -> {
                // do nothing for now
            }
        }
    }

    private fun setTheme(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}