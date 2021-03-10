package com.example.parrottest.utilities

import android.content.Context
import android.content.SharedPreferences

class PreferencesParrot(
    private val context: Context
) {

    companion object {

        private const val NAME_PREFERENCES = "PARROT_PREFERENCES"
        private const val USERNAME_PREFERENCE = "USERNAME_PREFERENCE"
        private const val KEY_PREFERENCE = "KEY_PREFERENCE"
    }

    fun setCurrentUsername(username: String) {

        val prefs: SharedPreferences =
            context.getSharedPreferences(NAME_PREFERENCES, Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(USERNAME_PREFERENCE, username)
        editor.apply()
    }

    fun getCurrentUsername(): String? {

        val prefs: SharedPreferences =
            context.getSharedPreferences(NAME_PREFERENCES, Context.MODE_PRIVATE)

        return prefs.getString(USERNAME_PREFERENCE, "")
    }

    fun setCurrentKey(accessToken: String) {

        val prefs: SharedPreferences =
            context.getSharedPreferences(NAME_PREFERENCES, Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(KEY_PREFERENCE, accessToken)
        editor.apply()
    }

    fun getCurrentKey(): String? {

        val prefs: SharedPreferences =
            context.getSharedPreferences(NAME_PREFERENCES, Context.MODE_PRIVATE)

        return prefs.getString(KEY_PREFERENCE, "")
    }
}