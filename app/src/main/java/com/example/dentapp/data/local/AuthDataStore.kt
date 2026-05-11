package com.example.dentapp.data.local

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.dentapp.domain.model.clinic.Clinic
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
        private val DOCTOR_ID = stringPreferencesKey("doctor_id")
        private val EMAIL = stringPreferencesKey("email")
        private val LAST_NAME = stringPreferencesKey("last_name")
        private val FIRST_NAME = stringPreferencesKey("first_name")
        private val PATRONYMIC = stringPreferencesKey("patronymic")
        private val SEX = stringPreferencesKey("sex")
        private val CLINIC_ID = stringPreferencesKey("clinic_id")
        private val CLINIC_NAME = stringPreferencesKey("clinic_name")
        private val CLINIC_ADDRESS = stringPreferencesKey("clinic_address")
        private val CLINIC_ORG_NAME = stringPreferencesKey("clinic_org_name")
        private val CLINIC_LOGO = stringPreferencesKey("clinic_logo")
        private val CLINIC_CONTACTS = stringPreferencesKey("clinic_contacts")
    }

    suspend fun saveAuthData(
        isLoggedIn: Boolean,
        accessToken: String? = null,
        refreshToken: String? = null,
        userId: String? = null,
        doctorId: String? = null,
        email: String? = null,
        lastName: String? = null,
        firstName: String? = null,
        patronymic: String? = null,
        sex: String? = null,
        clinic: Clinic? = null
    ) {
        dataStore.edit { prefs ->
            prefs[IS_LOGGED_IN] = isLoggedIn
            userId?.let { prefs[USER_ID] = it }
            doctorId?.let { prefs[DOCTOR_ID] = it }
            email?.let { prefs[EMAIL] = it }
            lastName?.let { prefs[LAST_NAME] = it }
            firstName?.let { prefs[FIRST_NAME] = it }
            patronymic?.let { prefs[PATRONYMIC] = it }
            sex?.let { prefs[SEX] = it }
            clinic?.let {
                prefs[CLINIC_ID] = it.id.toString()
                prefs[CLINIC_NAME] = it.shortName
                prefs[CLINIC_ADDRESS] = it.address
                prefs[CLINIC_ORG_NAME] = it.orgName
                prefs[CLINIC_CONTACTS] = it.contacts
                prefs[CLINIC_LOGO] = it.logo
            }
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

    suspend fun getDoctorId(): String? {
        return dataStore.data.map { it[DOCTOR_ID] }.first()// ?: "0c470601-cd8a-48bf-add9-4ee2b6a76b71"
    }

    suspend fun getEmail(): String? {
        return dataStore.data.map { it[EMAIL] }.first()
    }

    suspend fun getFirstName(): String? {
        return dataStore.data.map { it[FIRST_NAME] }.first()
    }

    suspend fun getPatronymic(): String? {
        return dataStore.data.map { it[PATRONYMIC] }.first()
    }

    suspend fun getFullName(): String? {
        return dataStore.data.map {
            val patronymic = it[PATRONYMIC] ?: ""
            "${it[LAST_NAME]} ${it[FIRST_NAME]}$patronymic"
        }.first()
    }

    suspend fun getSex(): String? {
        return dataStore.data.map { it[SEX] }.first()
    }

    suspend fun getClinicId(): String? {
        return dataStore.data.map { it[CLINIC_ID] }.first()
    }

    suspend fun getClinicLogo(): String? {
        return dataStore.data.map { it[CLINIC_LOGO] }.first()
    }

    suspend fun getClinicOrgName(): String? {
        return dataStore.data.map { it[CLINIC_ORG_NAME] }.first()
    }

    suspend fun getClinicName(): String? {
        return dataStore.data.map { it[CLINIC_NAME] }.first()
    }

    suspend fun getClinicAddress(): String? {
        return dataStore.data.map { it[CLINIC_ADDRESS] }.first()
    }
    suspend fun getClinicContacts(): String? {
        return dataStore.data.map { it[CLINIC_CONTACTS] }.first()
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