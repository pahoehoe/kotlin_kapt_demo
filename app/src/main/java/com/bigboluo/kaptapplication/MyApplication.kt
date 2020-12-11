package com.bigboluo.kaptapplication

import android.app.Application
import com.example.commands.Commands

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Commands.init(this)
    }

}