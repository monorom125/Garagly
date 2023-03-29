package com.rom.garagely.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import java.lang.reflect.Type

class PreferencesManager(context: Context, val name : String) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    // commit function is synchronous
    @SuppressLint("ApplySharedPref")
    fun commit(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).commit()
    }

    fun get(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, 0)
    }

    // store function is asynchronous
    fun store(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun store(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun store(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun remove(vararg keys: String) {
        keys.forEach { remove(it) }
    }

    @SuppressLint("ApplySharedPref")
    fun clear() {
        sharedPreferences.edit().clear().commit()
    }

    companion object {
        private var currentInstance: PreferencesManager? = null

        @Synchronized
        fun instantiate(context: Context, name: String): PreferencesManager? {
            if (currentInstance != null) {
                return currentInstance
            }
            currentInstance = PreferencesManager(context, name)
            return currentInstance
        }

        val instance: PreferencesManager
            get() {
                if (currentInstance != null) {
                    return currentInstance!!
                }
                throw IllegalStateException(PreferencesManager::class.java.simpleName + " is not initialized, please instantiate(...) method first!")
            }
    }
}