package com.example.parahabit.timer

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.widget.Toast
import com.example.parahabit.data.models.Habit


interface ITimerCallback{
    fun onTick(time: Long)
    fun onFinish(time: Long)
    fun getTimerHabit(): Habit?
}

class TimerService: Service() {
    private lateinit var timer: CountDownTimer

    private val binder = ServiceBinder()
    private var callback: ITimerCallback? = null

    // TODO: można tutaj wstawić callbackki
    // TODO: tutaj będzie trzeba jakoś przesłać callbacki

    fun subscribe(callback: ITimerCallback){
        this.callback = callback
    }

    private val callbacks2: MutableList<TimerCallback> = ArrayList()
    fun unsubscribe(callback: TimerCallback){
        callbacks2.remove(callback)
    }

    override fun onBind(intent: Intent?): IBinder? {
        println("onBind")
//        time = intent!!.getLongExtra("time", 0L)
//        println(time)

        return binder
    }

    fun start(time: Long){
        println("Zaczynamy " + time)
        Toast.makeText(this, "Service stared", Toast.LENGTH_LONG).show()
        timer = object: CountDownTimer(time * 1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                println("Tick")
                val seconds = millisUntilFinished/1000

                callback?.onTick(seconds)
            }

            override fun onFinish() {
                println("Zakończono odliczanie")
                callback?.onFinish(0)
            }
        }
        timer.start()
    }

    fun stop(){
        timer.cancel() // TODO: psprawdzić, czy to będzie można uruchomić później
//        callback?.onFinish()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        return super.onStartCommand(intent, flags, startId)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_LONG).show()
    }

    inner class ServiceBinder: Binder(){
        fun getService(): TimerService = this@TimerService
    }

}