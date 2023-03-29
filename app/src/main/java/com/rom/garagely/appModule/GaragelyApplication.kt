package com.rom.garagely.appModule

import android.app.Application
import com.rom.garagely.common.PreferencesManager
import com.rom.garagely.constant.SharedPreferenceKeys
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GaragelyApplication : Application(){


    override fun onCreate() {
        super<Application>.onCreate()

        PreferencesManager.instantiate(this, SharedPreferenceKeys.PREF_NAME)

    }
}