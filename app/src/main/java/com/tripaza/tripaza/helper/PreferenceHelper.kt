package com.tripaza.tripaza.helper

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databases.dataobject.User

class PreferencesHelper(val context: Context){
    companion object{
        private const val TAG = "PreferenceHelper"
        private const val USER_ID = "userId"
        private const val NAME = "name"
        private const val DOB = "dob"
        private const val PHONE = "phone"
        private const val EMAIL = "email"
    }
    private val pref = context.getSharedPreferences(context.getString(R.string.APP_PREFERENCES_NAME), Context.MODE_PRIVATE)
    
    fun setUser(u: User){
        val editor = pref.edit()
        editor.putString(USER_ID, u.id)
        editor.putString(NAME, u.name)
        editor.putString(DOB, u.dob)
        editor.putString(PHONE, u.phone)
        editor.putString(EMAIL, u.email)
        editor.apply()
    }
    
    fun getUser(): User{
        return User(
            id = pref.getString(USER_ID,"").toString(),
            name = pref.getString(NAME,"").toString()
        )
    }
    
    fun isDarkMode(): Boolean{
        return pref.getBoolean(context.getString(R.string.APP_PREFERENCES_DARK_MODE_KEY), false)
    }
}