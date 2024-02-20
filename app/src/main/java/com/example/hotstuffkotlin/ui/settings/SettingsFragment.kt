package com.example.hotstuffkotlin.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.hotstuffkotlin.BuildConfig
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.models.Building
import com.example.hotstuffkotlin.utils.DatabaseHelper
import com.example.hotstuffkotlin.utils.PREF_VERSION
import com.example.hotstuffkotlin.utils.SharedPreferenceHelper

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var preferenceHelper: SharedPreferenceHelper

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        preferenceHelper = SharedPreferenceHelper.getInstance(requireContext())

        val version = findPreference<Preference>(PREF_VERSION)
        version?.summary = BuildConfig.VERSION_NAME
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
            getString(R.string.key_theme) -> {
                when (preferenceHelper.getThemePref()) {
                    getString(R.string.theme_light) -> setTheme(AppCompatDelegate.MODE_NIGHT_NO)
                    getString(R.string.theme_dark) -> setTheme(AppCompatDelegate.MODE_NIGHT_YES)
                    getString(R.string.theme_system) -> setTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
            getString(R.string.key_currency) -> {
                // do nothing for now
            }
            getString(R.string.key_buildingName),
            getString(R.string.key_buildingType),
            getString(R.string.key_buildingDesc) -> {
                val building = Building()
                building.name = preferenceHelper.getPref(key).toString()
                building.type = preferenceHelper.getPref(key).toString()
                building.description = preferenceHelper.getPref(key).toString()
                DatabaseHelper(requireContext()).updateBuilding(building)
            }
        }
    }

    private fun setTheme(mode: Int) {
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}