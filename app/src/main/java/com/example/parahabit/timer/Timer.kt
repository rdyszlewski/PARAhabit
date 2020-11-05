package com.example.parahabit.timer

import android.content.Context
import android.content.Intent

typealias TimerCallback = (Long) -> Unit

class Timer(private val context: Context) : ITimerCallback {

    private var timerId: Long = -1
    private val callbacksManager: TimerCallbacksManager = TimerCallbacksManager()
    private var state: TimerState = TimerState.STOPPED

    private val connection = TimerServiceConnection(this)

    init {
        println("Inicjowanie licznika")
        val intent = Intent(context, TimerService::class.java)
        intent.also { serviceIntent -> context.bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE) }
    }

    fun start(timerId: Long, time: Long) {
        setupStartingTimer(timerId)
        this.timerId = timerId
        connection.getService().start(time)
        state = TimerState.STARTED

    }

    private fun setupStartingTimer(timerId: Long) {
        if (timerId == this.timerId) {
            if (state == TimerState.STARTED) {
                throw IllegalStateException("Timer is running already")
            }
        } else if (this.timerId >= 0) {
            stop()
        }
    }

    fun stop() {
        connection.getService().stop()
        onFinish(connection.getService().getTime())
    }

    override fun onTick(time: Long) {
        callbacksManager.onTick(time, timerId)
    }

    override fun onFinish(time: Long) {
        callbacksManager.onFinish(time, timerId)
        state = TimerState.STOPPED
    }

    override fun getTimerId(): Long {
        return timerId
    }

    fun isStarted(): Boolean {
        return state == TimerState.STARTED
    }

    fun isActiveTimer(id: Long): Boolean {
        return timerId == id
    }

    fun subscribe(callback: ITimerCallback) {
        callbacksManager.subscribe(callback)
    }

    fun unsubscribe(callback: ITimerCallback) {
        callbacksManager.unsubscribe(callback)
    }
}