package com.pfortbe22bgrupo2.parcialtp3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.pfortbe22bgrupo2.parcialtp3.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        applySettings()

    }


    private fun applySettings() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val nightMode = prefs.getBoolean("night_mode_switch_preferences",false)
        if (nightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        delegate.applyDayNight()
    }
}