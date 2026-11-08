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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>, private val encryptedPrefs: SharedPreferences
) {
    companion object {
        private val IS_OFFLINE_MODE = booleanPreferencesKey("is_offline_mode")
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
            prefs[IS_OFFLINE_MODE] = false
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

    suspend fun setOfflineModeAuth(
    ) {
        dataStore.edit { prefs ->
            prefs[IS_OFFLINE_MODE] = true
            prefs[CLINIC_NAME] = "Арт Класс"
            prefs[CLINIC_ADDRESS] = "Санкт-Петербург, \n" +
                    "Ланское шоссе, д. 65"
            prefs[CLINIC_ORG_NAME] = "ООО \"Арт Класс\" СК\""
            prefs[CLINIC_CONTACTS] = "+7 (812) 492-90-00\n" +
                    "+7 (812) 715-82-17\n" +
                    "mail@ak-stom.ru"
            prefs[CLINIC_LOGO] = "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"
        }
    }

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { prefs -> prefs[IS_LOGGED_IN] ?: false }

    val isOfflineMode: Flow<Boolean> = dataStore.data.map { prefs -> prefs[IS_OFFLINE_MODE] ?: false }

    suspend fun getUserId(): String? {
        return dataStore.data.map { it[USER_ID] }.first()
    }

    suspend fun getDoctorId(): String? {
        return try {
            dataStore.data.map { it[DOCTOR_ID] }.firstOrNull()
        } catch (_: Exception) {
            null
        }
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
        }.firstOrNull() ?: ""
    }

    suspend fun getSex(): String? {
        return dataStore.data.map { it[SEX] }.first()
    }

    suspend fun getClinicId(): String? {
        return dataStore.data.map { it[CLINIC_ID] }.firstOrNull()
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