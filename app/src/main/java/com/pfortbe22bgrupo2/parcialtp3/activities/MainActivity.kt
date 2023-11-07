package com.pfortbe22bgrupo2.parcialtp3.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.navigation.NavigationView
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.databinding.ActivityMainBinding
import com.pfortbe22bgrupo2.parcialtp3.fragments.AdoptedFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.DetailsFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.FavoritesFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.HomeFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.ProfileFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.PublicationFragment
import com.pfortbe22bgrupo2.parcialtp3.fragments.SettingsFragment
import com.pfortbe22bgrupo2.parcialtp3.viewmodels.AdoptedViewModel
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    DetailsFragment.OnAdoptedFragmentChangeListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
    private lateinit var adoptedViewModel: AdoptedViewModel

    override fun updateAdoptionsBadge(count: Int) {
        val badge: BadgeDrawable = binding.bottomNavigation.getOrCreateBadge(R.id.adoptedFragment2)
        badge.isVisible = true
        badge.number = count
    }

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
        adoptedViewModel = ViewModelProvider(this).get(AdoptedViewModel::class.java)
        setContentView(binding.root)
        applySettings()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
        //Hacemos que la Toolbar actue como ActionBar
        setSupportActionBar(binding.toolbar)

        //Animacion para Drawer
        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.openNavDrawer, R.string.closeNavDrawer)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //Hacemos que esta actividad sea notificada cuando un menuItem sea seleccionado
        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        //Funcionamiento de bottomNav
        binding.bottomNavigation.background = null
        setBadgeCount()
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

        deselectBottomMenuItems()

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

    private fun setBadgeCount() {
        val pref = this.getSharedPreferences("user", Context.MODE_PRIVATE)
        val userName = pref.getString("userName", "") ?: ""

        adoptedViewModel.totalAdoptionsNumber.observe(this) { totalAdoptions ->
            val badge: BadgeDrawable = binding.bottomNavigation.getOrCreateBadge(R.id.adoptedFragment2)
            badge.isVisible = true
            badge.number = totalAdoptions
        }
        adoptedViewModel.loadAdoptedListTotal(userName)
    }

    fun deselectBottomMenuItems() {
        binding.bottomNavigation.menu.setGroupCheckable(0, true, false)
        for (i in 0 until binding.bottomNavigation.menu.size()) {
            binding.bottomNavigation.menu.getItem(i).isChecked = false
        }
        binding.bottomNavigation.menu.setGroupCheckable(0, true, true)
    }

    fun selectBottomMenuItem(id: Int) {
        deselectBottomMenuItems()
        binding.bottomNavigation.selectedItemId = id
    }
}
