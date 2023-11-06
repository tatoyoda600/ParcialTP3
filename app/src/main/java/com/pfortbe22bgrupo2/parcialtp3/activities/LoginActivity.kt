package com.pfortbe22bgrupo2.parcialtp3.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.pfortbe22bgrupo2.parcialtp3.databinding.ActivityLoginBinding
import com.pfortbe22bgrupo2.parcialtp3.fillerdata.DogsList
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val databaseHandler = DatabaseHandler(binding.root.context)
        CoroutineScope(Dispatchers.IO).launch {
            if (databaseHandler.getAdoptionList().size < 1) {
                DogsList().fillDatabase(binding.root.context)
            }
        }

        applySettings()
        verifyCurrentUser()
    }

    private fun verifyCurrentUser() {
        val pref = this.getSharedPreferences("user", Context.MODE_PRIVATE)
        val userName = pref.getString("userName",null)
        if (userName != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
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