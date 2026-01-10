package com.example.dentapp.data.local

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>, private val encryptedPrefs: SharedPreferences
) {
    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_ID = stringPreferencesKey("user_id")
        private val EMAIL = stringPreferencesKey("email")
        private val FULL_NAME = stringPreferencesKey("full_name")
        private val FIRST_NAME = stringPreferencesKey("first_name")
        private val SEX = stringPreferencesKey("sex")
    }

    suspend fun saveAuthData(
        isLoggedIn: Boolean,
        accessToken: String? = null,
        refreshToken: String? = null,
        userId: String? = null,
        email: String? = null,
        fullName: String? = null,
        firstName: String? = null,
        sex: String? = null
    ) {
        dataStore.edit { prefs ->
            prefs[IS_LOGGED_IN] = isLoggedIn
            userId?.let { prefs[USER_ID] = it }
            email?.let { prefs[EMAIL] = it }
            fullName?.let { prefs[FULL_NAME] = it }
            firstName?.let { prefs[FIRST_NAME] = it }
            sex?.let { prefs[SEX] = it }
        }

        val editor = encryptedPrefs.edit()
        accessToken?.let { editor.putString("access_token", it) }
        refreshToken?.let { editor.putString("refresh_token", it) }
        editor.apply()
    }

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { prefs -> prefs[IS_LOGGED_IN] ?: false }

    suspend fun getUserId(): String? {
        return dataStore.data.map { it[USER_ID] }.first()
    }

    suspend fun getEmail(): String? {
        return dataStore.data.map { it[EMAIL] }.first()
    }

    suspend fun getFirstName(): String? {
        return dataStore.data.map { it[FIRST_NAME] }.first()
    }

    suspend fun getFullName(): String? {
        return dataStore.data.map { it[FULL_NAME] }.first()
    }

    suspend fun getSex(): String? {
        return dataStore.data.map { it[SEX] }.first()
    }

    fun getAccessTokenBlocking(): String? {
        return encryptedPrefs.getString("access_token", null)
    }

    fun getRefreshTokenBlocking(): String? {
        return encryptedPrefs.getString("refresh_token", null)
    }

    suspend fun getAccessToken(): String? = getAccessTokenBlocking()
    suspend fun getRefreshToken(): String? = getRefreshTokenBlocking()

    suspend fun clearAuthData() {
        dataStore.edit { it.clear() }
        val editor = encryptedPrefs.edit()
        editor.remove("access_token")
        editor.remove("refresh_token")
        editor.apply()
    }
}