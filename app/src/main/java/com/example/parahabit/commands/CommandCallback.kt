package com.example.parahabit.commands

import android.os.Handler
import android.os.Looper

class CommandCallback<T> {

    private var callback: ((T)->Unit)? = null
    private val mainLooper = Looper.getMainLooper()

    fun setCallback(callback: (T)->Unit){
        this.callback = callback
    }

    fun execute(element: T){
        if(callback != null){
            Handler(mainLooper).post{
                callback!!(element)
            }
        }
    }
}