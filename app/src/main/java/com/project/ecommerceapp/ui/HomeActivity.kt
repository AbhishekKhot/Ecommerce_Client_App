package com.project.ecommerceapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.ecommerceapp.databinding.ActivityMainBinding
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.ecommerceapp.R

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
       setTheme(R.style.Theme_EcommerceClientApp)
        setContentView(binding.root)
       supportActionBar?.hide()

        val navView: BottomNavigationView =binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration= AppBarConfiguration(setOf(
            R.id.fragmentHome, R.id.fragmentCart, R.id.fragmentUserAccount
        ))
        setupActionBarWithNavController(navController,appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}