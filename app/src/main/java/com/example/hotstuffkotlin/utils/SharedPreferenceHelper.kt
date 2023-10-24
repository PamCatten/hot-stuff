package com.example.hotstuffkotlin.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPreferenceHelper {

    companion object {
        private var prefs: SharedPreferences? = null

        @Volatile
        private var instance: SharedPreferenceHelper? = null

        fun getInstance(context: Context): SharedPreferenceHelper {
            synchronized(this) {
                val _instance = instance
                if (_instance == null) {
                    prefs = PreferenceManager.getDefaultSharedPreferences(context)
                    instance = _instance
                }
                return SharedPreferenceHelper()
            }
        }
    }
    fun getSelectedThemePref() = prefs?.getString("appearance", "")

}