package com.example.supportservice.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class DataStoreManager(val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences_name")
        private val FIRST_OPEN_KEY = booleanPreferencesKey("first_open_key")
        private val AGE_ID = intPreferencesKey("age_id")
        private val ECOLOGY_COMMON_SCORE = intPreferencesKey("ecology_common_score")
        val THE_FIRST_LOCALE_LANGUAGE_KEY = intPreferencesKey("THE_FIRST_LOCALE_LANGUAGE_KEY")
        val LANGUAGE_SHORTCUT_KEY = stringPreferencesKey("LANGUAGE_SHORTCUT_KEY")
        private val ADVICES_COMMON_SCORE = intPreferencesKey("advices_common_score")
        private val ECOLOGY_TEST_ID = intPreferencesKey("ecology_test_id")
        private val ADVICES_TEST_ID = intPreferencesKey("advices_test_id")
        private val LOGIN_KEY = stringPreferencesKey("login_key")
        private val PASSWORD_KEY = stringPreferencesKey("password_key")
    }


    suspend fun updateLogin(login: String) {
        context.dataStore.edit { preferences ->
            preferences[LOGIN_KEY] = login
        }
    }

    suspend fun updatePassword(password: String) {
        context.dataStore.edit { preferences ->
            preferences[PASSWORD_KEY] = password
        }
    }

    suspend fun updateEcologyCommonScore(score: Int) {
        context.dataStore.edit { prefences ->
            prefences[ECOLOGY_COMMON_SCORE] = score
        }
    }

    suspend fun updateLanguage(id: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_SHORTCUT_KEY] = id
        }
    }

    suspend fun updateLanguageState(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[THE_FIRST_LOCALE_LANGUAGE_KEY] = id
        }
    }

    suspend fun updateEcologyTestId(score: Int) {
        context.dataStore.edit { prefences ->
            prefences[ECOLOGY_TEST_ID] = score
        }
    }

    suspend fun updateAdvicesCommonScore(score: Int) {
        context.dataStore.edit { prefences ->
            prefences[ADVICES_COMMON_SCORE] = score
        }
    }

    suspend fun updateAdvicesTestId(score: Int) {
        context.dataStore.edit { prefences ->
            prefences[ADVICES_TEST_ID] = score
        }
    }

    suspend fun updateFirstOpenKey(key: Boolean) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[FIRST_OPEN_KEY] = key
        }
    }

    suspend fun updateAgeId(id: Int) {
        context.dataStore.edit { mutablePreferences ->
            mutablePreferences[AGE_ID] = id
        }
    }


    val getLogin = context.dataStore.data.map {
        it[LOGIN_KEY] ?: ""
    }

    val getPassword = context.dataStore.data.map {
        it[PASSWORD_KEY] ?: ""
    }

    val getLanguageState = context.dataStore.data.map {
        it[THE_FIRST_LOCALE_LANGUAGE_KEY] ?: 0
    }

    val getLanguageId = context.dataStore.data.map {
        it[LANGUAGE_SHORTCUT_KEY] ?: "tg"
    }

    val getAgeId = context.dataStore.data.map {
        it[AGE_ID] ?: 0
    }
    val getEcologyCommonScore = context.dataStore.data.map {
        it[ECOLOGY_COMMON_SCORE] ?: 0
    }
    val getEcologyTestId = context.dataStore.data.map {
        it[ECOLOGY_TEST_ID] ?: 0
    }
    val getAdvicesCommonScore = context.dataStore.data.map {
        it[ADVICES_COMMON_SCORE] ?: 0
    }
    val getAdvicesTestId = context.dataStore.data.map {
        it[ADVICES_TEST_ID] ?: 0
    }

    val getFirstOpenKey = context.dataStore.data.map {
        it[FIRST_OPEN_KEY] ?: false
    }

}