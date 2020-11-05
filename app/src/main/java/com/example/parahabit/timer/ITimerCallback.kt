package com.example.parahabit.timer

import com.example.parahabit.data.models.Habit

interface ITimerCallback {
    fun onTick(time: Long)
    fun onFinish(time: Long)
    fun getTimerId(): Long
}