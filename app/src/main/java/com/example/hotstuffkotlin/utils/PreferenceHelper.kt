package com.example.hotstuffkotlin.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.hotstuffkotlin.R

/**
 * A helper class to retrieve stored shared preferences from the [PreferenceManager].
 * @param context Used to push and receive information from the application environment.
 * @author Cam Patten
 */
class PreferenceHelper(val context: Context) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    /**
     * Set a Boolean shared preference value.
     * @param key The name of the preference to be set.
     * @param value The new value for the preference.
     * @author Cam Patten
     */
    fun putBooleanPref(key: String, value: Boolean = false) {
        prefs!!.edit().putBoolean(key, value).apply()
    }
    /**
     * Set a String shared preference value.
     * @param key The name of the preference to be set.
     * @param value The new value for the preference.
     * @author Cam Patten
     */
    fun putStringPref(key: String, value: String? = null) {
        prefs!!.edit().putString(key, value).apply()
    }
    /**
     * Retrieve a shared preference value of type String.
     * @param key The name of the preference to be retrieved.
     * @param defValue The value to be returned if the preference does not exist.
     * @return
     * Returns the preference value if it exists, or defValue.
     * @throws ClassCastException if there is a preference with the key that is not a String.
     * @author Cam Patten
     */
    fun getStringPref(key: String, defValue: String? = ""): String? {
        return prefs!!.getString(key, defValue)
    }
    /**
     * Retrieve a shared preference value of type Boolean.
     * @param key The name of the preference to be retrieved.
     * @param defValue The value to be returned if the preference does not exist.
     * @return
     * Returns the preference value if it exists, or defValue.
     * @throws ClassCastException if there is a preference with the key that is not a Boolean.
     * @author Cam Patten
     */
    fun getBooleanPref(key: String, defValue: Boolean = false): Boolean {
        return prefs!!.getBoolean(key, defValue)
    }
    /**
     * Sets the given shared preference value as the default theme for the activity.
     * @param themePreference The type of theme.
     * @author Cam Patten
     */
    fun applyTheme(themePreference: String?) {
        when (themePreference) {
            context.getString(R.string.theme_light) -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            context.getString(R.string.theme_dark) -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            context.getString(R.string.theme_system) -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
    /**
     * Retrieves the matching unicode currency icon for the given shared preference value.
     * @param currency The type of currency.
     * @author Cam Patten
     */
    fun getCurrencyIcon(currency: String?): String {
        val icon = when (currency) {
            context.getString(R.string.cur_bitcoin) -> R.string.icon_bitcoin
            context.getString(R.string.cur_dollar) -> R.string.icon_dollar
            context.getString(R.string.cur_dong) -> R.string.icon_dong
            context.getString(R.string.cur_euro) -> R.string.icon_euro
            context.getString(R.string.cur_guarani) -> R.string.icon_guarani
            context.getString(R.string.cur_hryvnia) -> R.string.icon_hryvnia
            context.getString(R.string.cur_kip) -> R.string.icon_kip
            context.getString(R.string.cur_lari) -> R.string.icon_lari
            context.getString(R.string.cur_lira) -> R.string.icon_lira
            context.getString(R.string.cur_manat) -> R.string.icon_manat
            context.getString(R.string.cur_naira) -> R.string.icon_naira
            context.getString(R.string.cur_peso) -> R.string.icon_peso
            context.getString(R.string.cur_pound) -> R.string.icon_pound
            context.getString(R.string.cur_ruble) -> R.string.icon_ruble
            context.getString(R.string.cur_rupee) -> R.string.icon_rupee
            context.getString(R.string.cur_shekel) -> R.string.icon_shekel
            context.getString(R.string.cur_tenge) -> R.string.icon_tenge
            context.getString(R.string.cur_tugrik) -> R.string.icon_tugrik
            context.getString(R.string.cur_won) -> R.string.icon_won
            context.getString(R.string.cur_yen_yuan) -> R.string.icon_yen_yuan
            else -> R.string.icon_dollar
        }
        return context.getString(icon)
    }

}