package com.example.profileapplication.di.language

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.Locale

class LanguageManager(private val context: Context) {
    private fun updateContextLocale(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }


    fun updateLanguage(language: String) {
        val newContext = updateContextLocale(language)
        (context as Activity).apply {
            applyOverrideConfiguration(newContext.resources.configuration)
            saveLanguagePreference(language)
            recreate() // Recreate the activity to apply the language change
        }
    }

    private fun saveLanguagePreference(language: String) {
        val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("My_Lang", language)
        editor.apply()
    }

    fun getSavedLanguage(): String {
        val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        return sharedPreferences.getString("My_Lang", "en") ?: "en"
    }
}