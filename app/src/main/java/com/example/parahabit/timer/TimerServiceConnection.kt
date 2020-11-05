package com.example.parahabit.timer

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

class TimerServiceConnection(val callback: ITimerCallback): ServiceConnection {

    private var bound = false
    private lateinit var timerService: TimerService

    fun getService(): TimerService{
        return timerService
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        println("onServiceConnected")
        val binder = service as TimerService.ServiceBinder
        timerService = binder.getService()
        timerService.subscribe(callback)
//        timerService.subscribe(object:ITimerCallback{
//            override fun onTick(time: Long) {
//                onTimerTick(time)
//            }
//
//            override fun onFinish(time:Long) {
//                onTimerFinish()
//            }
//
//            override fun getTimerHabit(): Habit? {
//                // TODO: zastanowić się, czy to wszystko ma sens
//                return habit
//            }
//
//        })
        bound = true
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        println("OnServiceDisconnected")
        bound = false
    }
}