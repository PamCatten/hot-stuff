package com.hotstuff.ui.settings

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.hotstuff.BuildConfig
import com.hotstuff.R
import com.hotstuff.utils.DatabaseHelper
import com.hotstuff.utils.PreferenceHelper

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        preferenceHelper = PreferenceHelper(requireContext())

        val rate = findPreference<Preference>(getString(R.string.key_rate))
        rate?.setOnPreferenceClickListener {
            // do nothing for now
            true
        }

        val feedback = findPreference<Preference>(getString(R.string.key_feedback))
        feedback?.setOnPreferenceClickListener {
            visitDestination(getString(R.string.link_issue))
            true
        }

        val reportBug = findPreference<Preference>(getString(R.string.key_report))
        reportBug?.setOnPreferenceClickListener {
            visitDestination(getString(R.string.link_issue))
            true
        }

        val email = findPreference<Preference>(getString(R.string.key_email))
        email?.setOnPreferenceClickListener {
            sendEmail(requireContext())
            true
        }
        val share = findPreference<Preference>(getString(R.string.key_share))
        share?.setOnPreferenceClickListener {
            shareApp()
            true
        }
        val terms = findPreference<Preference>(getString(R.string.key_terms))
        terms?.setOnPreferenceClickListener {
            visitDestination(getString(R.string.link_terms))
            true
        }
        val privacy = findPreference<Preference>(getString(R.string.key_privacy))
        privacy?.setOnPreferenceClickListener {
            visitDestination(getString(R.string.link_privacy))
            true
        }
        val sourceCode = findPreference<Preference>(getString(R.string.key_source_code))
        sourceCode?.setOnPreferenceClickListener {
            visitDestination(getString(R.string.link_repo))
            true
        }
        val openSource = findPreference<Preference>(getString(R.string.key_open_source))
        openSource?.setOnPreferenceClickListener {
            startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            true
        }

        val version = findPreference<Preference>(getString(R.string.key_version))
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
                val theme = preferenceHelper.getStringPref(getString(R.string.key_theme), getString(R.string.theme_system))
                PreferenceHelper(requireContext()).applyTheme(theme)
            }
            getString(R.string.key_currency) -> {}
            getString(R.string.key_buildingName),
            getString(R.string.key_buildingType),
            getString(R.string.key_buildingDesc) -> {
                val building = com.hotstuff.models.Building()
                building.name = preferenceHelper.getStringPref(key).toString()
                building.type = preferenceHelper.getStringPref(key).toString()
                building.description = preferenceHelper.getStringPref(key).toString()
                DatabaseHelper(requireContext()).updateBuilding(building)
            }
        }
    }

    private fun shareApp() {
        // TODO: ADD LINK TO PLAY STORE AND OPEN IN NEW JAVA INTENT
    }

    /**
     * Creates an intent to open the provided link.
     * @param link The URL of the intended destination.
     * @throws ActivityNotFoundException If no app capable of open the link is found on the device.
     * @author Cam Patten
     */
    private fun visitDestination(link: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, R.string.toast_no_app, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
    /**
     * Creates an intent to send an email to the developer.
     * @param context Used to receive information from the application environment.
     * @throws ActivityNotFoundException If no app capable of sending an email is found on the device.
     * @author Cam Patten
     */
    private fun sendEmail(context: Context) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(getString(R.string.email_data))
            val emails = arrayOf(getString(R.string.email_address))
            val subject = getString(R.string.email_subject)
            putExtra(Intent.EXTRA_EMAIL, emails)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            val packageName = context.packageName
            val packageInfo = context.packageManager.getPackageInfo(packageName, 0)
            val text = getString(R.string.email_text, Build.MODEL, Build.VERSION.RELEASE,
                packageInfo.versionName)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, R.string.toast_no_app, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}