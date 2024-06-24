package com.example.infinite_android_base.database

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferences(private val dataStore: DataStore<Preferences>) {
    val isLinearLayout: Flow<Boolean> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
        preferences[IS_LIKE_YOU] ?: true
    }
    private companion object {
        val IS_LIKE_YOU = booleanPreferencesKey("is_like_you")
        const val TAG = "UserPreferencesRepo"
    }
    suspend fun saveLayoutPreference(isLinearLayout: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LIKE_YOU] = isLinearLayout
        }
    }


    suspend fun getString(key: String, defaultValue:String? = null):String? {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)] ?: defaultValue
        }.last()
    }

    suspend fun getInt(key: String, defaultValue:Int? = null): Int? {
        return dataStore.data.map { preferences ->
            preferences[intPreferencesKey(key)] ?: defaultValue
        }.last()
    }

    suspend fun getDouble(key: String, defaultValue:Double? = null): Double? {
        return dataStore.data.map { preferences ->
            preferences[doublePreferencesKey(key)] ?: defaultValue
        }.last()
    }
    suspend fun getBoolean(key: String, defaultValue:Boolean? = null): Boolean? {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: defaultValue
        }.last()
    }

    suspend fun getFloat(key: String, defaultValue:Float? = null): Float? {
        return dataStore.data.map { preferences ->
            preferences[floatPreferencesKey(key)] ?: defaultValue
        }.last()
    }
    suspend fun getLong(key: String, defaultValue:Long? = null): Long? {
        return dataStore.data.map { preferences ->
            preferences[longPreferencesKey(key)] ?: defaultValue
        }.last()
    }
    suspend fun getStringSet(key: String, defaultValue:Set<String>? = null): Set<String>? {
        return dataStore.data.map { preferences ->
            preferences[stringSetPreferencesKey(key)] ?: defaultValue
        }.last()
    }
}