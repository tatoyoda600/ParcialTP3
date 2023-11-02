package com.pfortbe22bgrupo2.parcialtp3.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pfortbe22bgrupo2.parcialtp3.R
import com.pfortbe22bgrupo2.parcialtp3.fragments.DetailsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = DetailsFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.detailsFragment, fragment)
            .commit()
    }
}
