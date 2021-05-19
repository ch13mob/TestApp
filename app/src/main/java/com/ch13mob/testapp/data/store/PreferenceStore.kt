package com.ch13mob.testapp.data.store

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class PreferenceStore @Inject constructor(
    private val prefs: SharedPreferences
) {

    var email: String?
        get() = prefs.getString(EMAIL_KEY, null)
        set(value) = prefs.edit { putString(EMAIL_KEY, value) }

    var password: String?
        get() = prefs.getString(PASSWORD_KEY, null)
        set(value) = prefs.edit { putString(PASSWORD_KEY, value) }

    fun clearCredentials() {
        prefs.edit {
            remove(EMAIL_KEY)
            remove(PASSWORD_KEY)
        }
    }

    companion object {
        private const val EMAIL_KEY = "email_key"
        private const val PASSWORD_KEY = "password_key"
    }
}
