package com.example.androidsprint01

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidsprint01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        _binding = binding
        setContentView(binding.root)
    }
}