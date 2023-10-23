package com.example.hotstuffkotlin.model
import com.chibatching.kotpref.KotprefModel
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.hotstuffkotlin.R.string.*

object State : KotprefModel() {

    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }
    private fun nightModeString(): String? = sharedPreferences.getString(preference_key_nightmode.toString(), "system")
    fun getNightMode() = when (nightModeString()) {
        "light" -> AppCompatDelegate.MODE_NIGHT_NO
        "dark" -> AppCompatDelegate.MODE_NIGHT_YES
        "system" -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    fun applyDayNightMode() {
        AppCompatDelegate.setDefaultNightMode(getNightMode())
    }
}