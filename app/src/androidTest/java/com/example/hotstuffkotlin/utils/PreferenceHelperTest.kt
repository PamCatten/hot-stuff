package com.example.hotstuffkotlin.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class PreferenceHelperTest {
    private lateinit var context: Context
    private lateinit var prefs: SharedPreferences
    @Before
    fun initTest() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Test
    fun updateBuildingPrefs() {
    }
    @Test
    fun `GIVEN a valid key WHEN getStringPref is called THEN the corresponding value is returned`() {
        val validKey = "buildingName"
        val preferenceValue = "preference value"
        val defaultValue = "default value"
        prefs.edit().putString(validKey, preferenceValue).apply()
        val actual = PreferenceHelper(context).getStringPref(validKey, defaultValue)
        assertEquals(preferenceValue, actual)
    }
    @Test
    fun `GIVEN an invalid key WHEN getStringPref is called THEN the default value is returned`() {
        val invalidKey = "test"
        val defaultValue = "default value"
        val actual = PreferenceHelper(context).getStringPref(invalidKey, defaultValue)
        assertEquals(defaultValue, actual)
    }
    // TODO: ADD TEST FOR CLASS CAST EXCEPTION
    @Test
    fun `GIVEN a valid key WHEN getBooleanPref is called THEN the corresponding value is returned`() {
        val validKey = "onboard"
        val defaultBool = true
        prefs.edit().putBoolean(validKey, defaultBool).apply()
        val prefValue = PreferenceHelper(context).getBooleanPref(validKey)
        assertEquals(defaultBool, prefValue)
    }
    @Test
    fun `GIVEN an invalid key WHEN getBooleanPref is called THEN the default value is returned`() {
        val invalidKey = "test"
        val defaultBool = true
        val prefValue = PreferenceHelper(context).getBooleanPref(invalidKey, defaultBool)
        assertEquals(defaultBool, prefValue)
    }
    @Test
    fun `GIVEN an invalid key AND no default value WHEN getBooleanPref is called THEN the function default value is returned`() {
        val invalidKey = "test"
        val prefValue = PreferenceHelper(context).getBooleanPref(invalidKey)
        assertEquals(false, prefValue)
    }
    // TODO: ADD TEST FOR CLASS CAST EXCEPTION
    @Test
    fun `GIVEN a valid string WHEN applyTheme is called THEN the corresponding theme is set`() {
        val validThemeString = "light"
        PreferenceHelper(context).applyTheme(validThemeString)
        assertEquals(AppCompatDelegate.MODE_NIGHT_NO, AppCompatDelegate.getDefaultNightMode())
    }
    @Test
    fun `GIVEN an invalid string WHEN applyTheme is called THEN the default theme is set`() {
        val invalidThemeString = "test"
        PreferenceHelper(context).applyTheme(invalidThemeString)
        assertEquals(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, AppCompatDelegate.getDefaultNightMode())
    }
    @Test
    fun `GIVEN a valid string WHEN getCurrencyIcon is called THEN the correct icon is returned`() {
        val currencyString = "euro"
        val icon = PreferenceHelper(context).getCurrencyIcon(currencyString)
        assertEquals("€", icon)
    }
    @Test
    fun `GIVEN an invalid string WHEN getCurrencyIcon is called THEN the default icon is returned`() {
        val currencyString = "test"
        val icon = PreferenceHelper(context).getCurrencyIcon(currencyString)
        assertEquals("$", icon)
    }
}