package com.example.androidsprint01

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidsprint01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private fun setStatusAndNavigationBarColor() {
        val customColor = getColor(R.color.fon_categories)
        window.statusBarColor = customColor
        window.navigationBarColor = customColor
    }
}