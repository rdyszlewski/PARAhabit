package com.example.parahabit.timer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.CountDownTimer
import android.os.IBinder
import com.example.parahabit.commands.AddExecutionCommand
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.repository.Repository
import java.lang.IllegalStateException
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

typealias TimerCallback = (Long)->Unit

class Timer(private val context: Context) {
    // TODO: prawdopdobnie to powinno być w jakiś serwisie czy coś
    private var habit: Habit? = null;
    private val callbacks: MutableList<ITimerCallback> = ArrayList()
    private lateinit var timerService: TimerService
    private var bound = false
    private var currentTime: Long = 0

    private val unsubscribedCallbacks: MutableList<ITimerCallback> = ArrayList()

    private val connection = object: ServiceConnection{

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            println("onServiceConnected")
            val binder = service as TimerService.ServiceBinder
            timerService = binder.getService()
            timerService.subscribe(object:ITimerCallback{
                override fun onTick(time: Long) {
                    onTimerTick(time)
                }

                override fun onFinish() {
                    onTimerFinish()
                }

                override fun getTimerHabit(): Habit? {
                    // TODO: zastanowić się, czy to wszystko ma sens
                    return habit
                }

            })
            bound = true
            // TODO: wystartowanie serwisu

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            println("OnServiceDisconnected")
            bound = false
        }
    }

    private fun onTimerTick(time: Long){
        currentTime++
        println(currentTime)
        for(callback in callbacks){
            callback.onTick(time)
        }
    }

    private fun onTimerFinish(){
        println(callbacks.size)
        callbacks.forEach{
            if(it.getTimerHabit()==habit){
                it.onFinish()
            }
        }
        unsubscribedCallbacks.forEach{callbacks.remove(it)}
        println("Usuwanie zależności")
        println(unsubscribedCallbacks.size)
        unsubscribedCallbacks.clear()

        saveInDatabase()
        state = TimerState.STOPPED
        currentTime = 0
    }

    private fun saveInDatabase() {
        val command = AddExecutionCommand(habit!!, currentTime.toInt(), Repository.getInstance())
        command.execute()
    }

    fun subscribe(callback: ITimerCallback){
        callbacks.add(callback)
    }

    fun unsubscribe(callback: ITimerCallback){
        unsubscribedCallbacks.add(callback)
    }

    fun unsubscribeAll(){
        callbacks.clear()
    }


    fun setHabit(habit: Habit){
        this.habit = habit
    }

    fun getHabit():Habit?{
        return habit
    }

    enum class TimerState{
        STARTED,
        PAUSED,
        STOPPED
    }

    private lateinit var timer: CountDownTimer
    private var isRunning: Boolean = false
    var state: TimerState = TimerState.STOPPED
        get() = field



    fun start(habit: Habit){

        if( this.habit == habit){
            if(state == TimerState.STARTED){
                throw IllegalStateException("Timer is running already")
            }
        } else if(this.habit != null) {
            println("Zakończono stare zadanie")
            timerService.stop()
            onTimerFinish()
        }
        this.habit = habit


        println("Rozpoczęto nowe zadanie")
        val time = habit.goal - habit.getExecutionsValue() // TODO: sprawdzić, czy to będzie potprawnie. Trzeba sprawdzić, z ilu dni to liczy\
        println(time)
        timerService.start(time.toLong())
        state = TimerState.STARTED

    }

    private fun cleanCallbacks(currentHabit: Habit){
        println("Usuwanie callbacków")
        println(callbacks.size)
        val validCallbacks = callbacks.filter { x->x.getTimerHabit() == currentHabit }
        callbacks.clear()
        for(callback in validCallbacks){
            callbacks.add(callback)
        }
        println(callbacks.size)
    }



    fun stop(){
        timerService.stop()
        onTimerFinish()
        unsubscribeAll()
    }

    fun pause(){
        state = TimerState.PAUSED

    }


    fun getTime():Int{
        return 1
    }

    init {
        println("Inicjowanie licznika")
        val intent = Intent(context, TimerService::class.java)
//        intent.putExtra("time", habit.goal.toLong())
        intent.also { serviceIntent -> context.bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE) }
    }


}