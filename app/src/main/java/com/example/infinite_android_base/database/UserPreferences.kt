package com.example.infinite_android_base.database

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val dataStore: DataStore<Preferences>) {

    fun getString(key: String, defaultValue:String = "unKnow"):Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)] ?: defaultValue
        }
    }

    fun getInt(key: String, defaultValue:Int = -1): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[intPreferencesKey(key)] ?: defaultValue
        }
    }

    fun getDouble(key: String, defaultValue:Double = -1.0): Flow<Double> {
        return dataStore.data.map { preferences ->
            preferences[doublePreferencesKey(key)] ?: defaultValue
        }
    }
    fun getBoolean(key: String, defaultValue:Boolean = false): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: defaultValue
        }
    }

    fun getFloat(key: String, defaultValue:Float = -1.0f): Flow<Float> {
        return dataStore.data.map { preferences ->
            preferences[floatPreferencesKey(key)] ?: defaultValue
        }
    }
    fun getLong(key: String, defaultValue:Long = 1L): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[longPreferencesKey(key)] ?: defaultValue
        }
    }
    fun getStringSet(key: String, defaultValue:Set<String> = setOf()): Flow<Set<String>>?{
        return dataStore.data.map { preferences ->
            preferences[stringSetPreferencesKey(key)] ?: defaultValue
        }
    }
    @Suppress("UNCHECKED_CAST")
    suspend fun put(key: String, value: Any) {
        when (value::class) {
            String::class -> {
                dataStore.edit { settings ->
                    settings[stringPreferencesKey(key)] = value as String
                }
            }
            Int::class -> {
                dataStore.edit { settings ->
                    settings[intPreferencesKey(key)] = value as Int
                }
            }
            Double::class -> {
                dataStore.edit { settings ->
                    settings[doublePreferencesKey(key)] = value as Double
                }
            }
            Boolean::class -> {
                dataStore.edit { settings ->
                    settings[booleanPreferencesKey(key)] = value as Boolean
                }
            }
            Float::class -> {
                dataStore.edit { settings ->
                    settings[floatPreferencesKey(key)] = value as Float
                }
            }
            Long::class -> {
                dataStore.edit { settings ->
                    settings[longPreferencesKey(key)] = value as Long
                }
            }
            Set::class -> {
                dataStore.edit { settings ->
                    settings[stringSetPreferencesKey(key)] = value as Set<String>
                }
            }
            else -> {
                dataStore.edit { settings ->
                    settings[stringPreferencesKey(key)] = value.toString()
                }
            }
        }
    }


}