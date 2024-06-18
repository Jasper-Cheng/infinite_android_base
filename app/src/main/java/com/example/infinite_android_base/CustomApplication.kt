package com.example.infinite_android_base

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.infinite_android_base.database.ItemRoomDatabase
import com.example.infinite_android_base.database.UserPreferences

class CustomApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = LAYOUT_PREFERENCE_NAME
    )
    lateinit var userPreferences: UserPreferences

    override fun onCreate() {
        super.onCreate()
        userPreferences = UserPreferences(dataStore)
    }
    companion object{
        private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"

    }
}