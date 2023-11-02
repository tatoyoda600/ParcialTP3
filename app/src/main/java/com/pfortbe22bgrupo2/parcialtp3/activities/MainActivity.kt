package com.pfortbe22bgrupo2.parcialtp3.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.preference.PreferenceManager
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.databinding.ActivityMainBinding
import com.pfortbe22bgrupo2.parcialtp3.fragments.AdoptedFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.ConfigurationFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.FavoritesFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.HomeFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.LoginFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.ProfileFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.PublicationFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding : ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: SharedPreferences
    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        if (key == "night_mode_switch_preferences") {
            val nightMode = sharedPreferences.getBoolean(key, false)
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            delegate.applyDayNight()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Hacemos que la Toolbar actue como ActionBar
        setSupportActionBar(binding.toolbar)

        //Animacion para Drawer
        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,R.string.openNavDrawer, R.string.closeNavDrawer)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //Hacemos que esta actividad sea notificada cuando un menuItem sea seleccionado
        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        //Funcionamiento de bottomNav
        binding.bottomNavigation.background = null
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.homeFragment2 -> openFragment(HomeFragment())
                R.id.adoptedFragment2 -> openFragment(AdoptedFragment())
                R.id.publicationFragment2 -> openFragment(PublicationFragment())
                R.id.favoritesFragment2 -> openFragment(FavoritesFragment())
            }
            true
        }

        //Sacamos el indicador default de item seleccionado
        binding.bottomNavigation.itemActiveIndicatorColor = null


        fragmentManager = supportFragmentManager

        //Establecemos el Fragmento con el que inicia la aplicacion
        openFragment(LoginFragment())
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.profileFragment -> openFragment(ProfileFragment())
            R.id.configurationFragment -> openFragment(ConfigurationFragment())
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun openFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host, fragment)
        fragmentTransaction.commit()
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        applySettings()
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)

    }

    private fun applySettings() {
        val nightMode = prefs.getBoolean("night_mode_switch_preferences",false)
        if (nightMode){
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        }
        delegate.applyDayNight()
    }

}