package com.example.hotstuffkotlin.utils

import android.content.Context
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PreferenceHelperTest {

    @Mock
    private lateinit var mockContext: Context

    @Test
    fun updateBuildingPrefs() {
    }

    @Test
    fun getStringPref() {
    }

    @Test
    fun getBooleanPref() {
    }

    @Test
    fun applyThemePref() {
    }

//    @Test
//    fun `test getCurrencyIcon`() {
//        val mockContext = mock<Context>()
//        `when`(mockContext.getString(FAKE_RID)).thenReturn(FAKE_STRING)
//
//        val icon = PreferenceHelper.getInstance(mockContext).getCurrencyIcon(mockContext)
//        assertEquals(R.string.)
//    }

    companion object {
        private const val FAKE_RID = 12345
        private const val FAKE_STRING = "dollar"
    }
}