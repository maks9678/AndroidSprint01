package com.example.androidsprint01

import android.app.Application
import com.example.androidsprint01.di.AppConteiner

class RecipeApplication : Application() {
    lateinit var appConteiner: AppConteiner
    override fun onCreate() {
        super.onCreate()
        appConteiner = AppConteiner(this)
    }
}