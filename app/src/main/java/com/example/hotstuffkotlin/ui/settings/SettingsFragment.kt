package com.example.hotstuffkotlin.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.model.State
import com.example.hotstuffkotlin.utils.SharedPreferenceHelper

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        sharedPreferenceHelper = SharedPreferenceHelper.getInstance(requireContext())
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        val themePreferenceKey = "appearance"
        if (key == themePreferenceKey) {
            val themePreference = findPreference<Preference>(themePreferenceKey)
            val selectedOption = sharedPreferenceHelper.getSelectedThemePref()
            themePreference?.summary = selectedOption

            when (selectedOption) {
                getString(R.string.appearance_light) -> setTheme(AppCompatDelegate.MODE_NIGHT_NO)
                getString(R.string.appearance_dark) -> setTheme(AppCompatDelegate.MODE_NIGHT_YES)
                getString(R.string.appearance_system) -> setTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }

    private fun setTheme(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
    }

}