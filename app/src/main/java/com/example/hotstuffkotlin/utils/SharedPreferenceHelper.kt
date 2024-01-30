package com.example.hotstuffkotlin.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.hotstuffkotlin.R

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
    fun getThemePref() = prefs?.getString("theme", "system")
    fun getOnboardPref(): Boolean {
        return prefs?.getBoolean("onboard", true) == true
    }

    //TEST DELETE WHEN DONE WITH ONBOARD
    fun testOnboard() {
        prefs!!.edit().putBoolean("onboard", true).apply()
    }
    //END

    fun finishOnboarding() {
        prefs!!.edit().putBoolean("onboard", false).apply()
    }

    fun applyThemePref(themePreference: String?) {
        when (themePreference) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "system" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    fun getCurrencyPref(context : Context): String {
        val icon = when (prefs?.getString("currency", "dollar")) {
            context.getString(R.string.label_bitcoin) -> R.string.icon_bitcoin
            context.getString(R.string.label_dollar) -> R.string.icon_dollar
            context.getString(R.string.label_dong) -> R.string.icon_dong
            context.getString(R.string.label_euro) -> R.string.icon_euro
            context.getString(R.string.label_guarani) -> R.string.icon_guarani
            context.getString(R.string.label_hryvnia) -> R.string.icon_hryvnia
            context.getString(R.string.label_kip) -> R.string.icon_kip
            context.getString(R.string.label_lari) -> R.string.icon_lari
            context.getString(R.string.label_lira) -> R.string.icon_lira
            context.getString(R.string.label_manat) -> R.string.icon_manat
            context.getString(R.string.label_naira) -> R.string.icon_naira
            context.getString(R.string.label_peso) -> R.string.icon_peso
            context.getString(R.string.label_pound) -> R.string.icon_pound
            context.getString(R.string.label_ruble) -> R.string.icon_ruble
            context.getString(R.string.label_rupee) -> R.string.icon_rupee
            context.getString(R.string.label_shekel) -> R.string.icon_shekel
            context.getString(R.string.label_tenge) -> R.string.icon_tenge
            context.getString(R.string.label_tugrik) -> R.string.icon_tugrik
            context.getString(R.string.label_won) -> R.string.icon_won
            context.getString(R.string.label_yen_yuan) -> R.string.icon_yen_yuan
            else -> R.string.icon_dollar
        }
        return context.getString(icon)
    }

}