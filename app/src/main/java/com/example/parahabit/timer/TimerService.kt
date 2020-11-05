package com.example.parahabit.timer

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.widget.Toast
import com.example.parahabit.data.models.Habit


class TimerService: Service() {
    private lateinit var timer: CountDownTimer

    private val binder = ServiceBinder()
    private var callback: ITimerCallback? = null
    private var counter = 0L

    fun subscribe(callback: ITimerCallback){
        this.callback = callback
    }

    private val callbacks2: MutableList<TimerCallback> = ArrayList()
    fun unsubscribe(callback: TimerCallback){
        callbacks2.remove(callback)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    fun start(time: Long){
        Toast.makeText(this, "Service stared", Toast.LENGTH_LONG).show()
        counter = 0
        timer = object: CountDownTimer(time * 1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished/1000
                counter++
                callback?.onTick(seconds)
            }

            override fun onFinish() {
                callback?.onFinish(0)
            }
        }
        timer.start()
    }

    fun stop(){
        timer.cancel()
        // TODO: czy tutaj wydarzysię coś?
    }

    fun getTime():Long{
        return counter
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    inner class ServiceBinder: Binder(){
        fun getService(): TimerService = this@TimerService
    }

}