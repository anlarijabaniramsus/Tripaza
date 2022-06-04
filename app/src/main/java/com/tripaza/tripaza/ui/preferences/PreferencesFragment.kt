package com.tripaza.tripaza.ui.preferences

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databases.dataobject.User
import com.tripaza.tripaza.helper.PreferencesHelper
import com.tripaza.tripaza.ui.registration.MainActivity


class PreferencesFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var keyDarkMode: String
    
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.sharedPreferencesName = getString(R.string.APP_PREFERENCES_NAME)
        keyDarkMode = getString(R.string.APP_PREFERENCES_DARK_MODE_KEY)
        addPreferencesFromResource(R.xml.preferences)
        
        val btn = preferenceManager.findPreference<Preference>("logout")
        btn?.setOnPreferenceClickListener {
            logOutAlert()
            true
        }

    }

    private fun logout() {
        // CLEAR UP USER DATA
        PreferencesHelper(requireContext()).setUser(User())
        val intent = Intent(context, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        activity?.finish()
    }
    private fun logOutAlert(){
        val builder = AlertDialog.Builder(requireContext())
        with(builder){
            setTitle("Logout")
            setMessage("Are you sure?")
            setPositiveButton("yes, log me out") { _, _ ->
                logout()
            }
            setNegativeButton("Cancel", null)
            show()
        }
    }
    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }
    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when(key) {
            keyDarkMode ->{
                    if (sharedPreferences?.getBoolean(keyDarkMode, false) == true) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
            }
        }
    }
    
}