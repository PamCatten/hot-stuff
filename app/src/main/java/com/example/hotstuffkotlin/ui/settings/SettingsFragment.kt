package com.example.hotstuffkotlin.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.utils.SharedPreferenceHelper

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        sharedPreferenceHelper = SharedPreferenceHelper.getInstance(requireContext())
    }
}