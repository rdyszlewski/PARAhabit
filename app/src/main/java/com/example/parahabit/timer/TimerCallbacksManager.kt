package com.example.parahabit.timer

import com.example.parahabit.data.models.Habit

class TimerCallbacksManager{

    private val callbacks: MutableList<ITimerCallback> = ArrayList()
    private val unsubscribedCallbacks: MutableSet<ITimerCallback> = HashSet()

    fun subscribe(callback: ITimerCallback){
        callbacks.add(callback)
    }

    fun unsubscribe(callback: ITimerCallback){
        unsubscribedCallbacks.add(callback)
    }

    fun onTick(time: Long, timerId: Long) {
        callbacks.filter{ it.getTimerId()==timerId }.forEach{it.onTick(time)}
    }

    fun onFinish(time: Long, timerId: Long) {
        // TODO: zastanowić się czy to tak powinno być
        callbacks.filter { it.getTimerId()==timerId  && !unsubscribedCallbacks.contains(it)}.forEach { it.onFinish(time) }
        unsubscribedCallbacks.forEach{callbacks.remove(it)}
        unsubscribedCallbacks.clear()
    }
}