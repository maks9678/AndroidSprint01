package com.example.androidsprint01

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.androidsprint01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusAndNavigationBarColor()
    }

    private fun setStatusAndNavigationBarColor(
        fonStatusBar: Int = R.color.fon_status_bar,
        fonNavigationBar: Int = R.color.fon_navigation_bar
    ) {
        val statusBarColor = ContextCompat.getColor(this, fonStatusBar)
        val navigationBarColor = ContextCompat.getColor(this, fonNavigationBar)

        window.statusBarColor = statusBarColor
        window.navigationBarColor = navigationBarColor
    }
}