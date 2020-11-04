package com.example.parahabit

import android.app.Application
import com.example.parahabit.timer.Timer

class HabitApplication : Application() {

     var timer: Timer? = null
        get() = field

    override fun onCreate() {
        super.onCreate()
        timer = Timer(this)
    }

}
