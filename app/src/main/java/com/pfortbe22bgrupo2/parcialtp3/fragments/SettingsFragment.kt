package com.pfortbe22bgrupo2.parcialtp3.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.pfortbe22bgrupo2.parcialtp3.R


class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

    }

}