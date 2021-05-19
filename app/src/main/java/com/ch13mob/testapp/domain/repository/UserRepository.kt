package com.ch13mob.testapp.domain.repository

import com.ch13mob.testapp.data.store.PreferenceStore
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val preferenceStore: PreferenceStore
) {
    fun isUserLoggedIn(): Boolean {
        return preferenceStore.email.isNullOrEmpty().not()
    }

    fun login(email: String, password: String) {
        preferenceStore.email = email
        preferenceStore.password = password
    }

    fun logout() {
        preferenceStore.clearCredentials()
    }
}