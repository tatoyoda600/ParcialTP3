package com.pfortbe22bgrupo2.parcialtp3.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.pfortbe22bgrupo2.parcialtp3.R
import android.content.Context
import android.widget.TextView
import com.pfortbe22bgrupo2.parcialtp3.fillerdata.DogsList
import com.pfortbe22bgrupo2.parcialtp3.databinding.ActivityMainBinding
import com.pfortbe22bgrupo2.parcialtp3.fragments.SettingsFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.AdoptedFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.FavoritesFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.HomeFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.ProfileFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.PublicationFragment
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
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
        applySettings()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)

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
            supportActionBar?.title = item.title.toString()
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
        openFragment(HomeFragment())

        setDrawerHeaderName()
    }

    private fun setDrawerHeaderName() {
        val pref = this.getSharedPreferences("user", Context.MODE_PRIVATE)
        val userName = pref.getString("userName","").toString()
        val databaseHandler = DatabaseHandler(binding.root.context)
        CoroutineScope(Dispatchers.IO).launch {
            val user = databaseHandler.getUserByUsername(userName)
            val header = binding.navigationDrawer.getHeaderView(0)
            header.findViewById<TextView>(R.id.name_nav_header).text = user?.name ?: userName
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        supportActionBar?.title = item.title.toString()
        when(item.itemId){
            R.id.profileFragment -> openFragment(ProfileFragment())
            R.id.settingsFragment -> openFragment(SettingsFragment())
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)

        binding.bottomNavigation.menu.setGroupCheckable(0, true, false)
        for (i in 0 until binding.bottomNavigation.menu.size()) {
            binding.bottomNavigation.menu.getItem(i).isChecked = false
        }
        binding.bottomNavigation.menu.setGroupCheckable(0, true, true)

        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun openFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.navHost.id, fragment)
        fragmentTransaction.commit()
    }
}
