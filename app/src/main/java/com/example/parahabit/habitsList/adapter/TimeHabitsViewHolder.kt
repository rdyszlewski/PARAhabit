package com.example.parahabit.habitsList.adapter

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.parahabit.HabitApplication
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit
import com.example.parahabit.timer.ITimerCallback
import com.example.parahabit.timer.Timer
import com.google.android.material.button.MaterialButton

// TODO: zz godzinami prawdopodobnie będzie trzeba zrobić jakiś przelicznik
class TimeHabitsViewHolder(view: View, context: Activity) : HabitsViewHolder(view, context), ITimerCallback {

    private val nameText = view.findViewById<TextView>(R.id.name)
    private val doneText = view.findViewById<TextView>(R.id.done_text)
    private val startButton = view.findViewById<MaterialButton>(R.id.start_button)
    private val editTimeButton = view.findViewById<MaterialButton>(R.id.add_time_button)
    private val menuButton = view.findViewById<MaterialButton>(R.id.menu_button)
    private val progressBar = view.findViewById<ProgressBar>(R.id.progress)
    private val timeText = view.findViewById<TextView>(R.id.time_text)

    private val doneHelper = DoneViewHelper.Builder(view).setDoneText(doneText)
            .setInvisibleViews(listOf(startButton, editTimeButton)).build()

    private var timer: Timer? = null

    override fun bind(habit: Habit) {
        if (timer == null) {
            initTimer(habit)
        }
        this.habit = habit
        nameText.text = habit.name
        startButton.setOnClickListener {
            onTimerClick(habit)
        }
        progressBar.max = habit.goal
        updateView(habit)
    }

    private fun onTimerClick(habit: Habit) {
        if (isActiveHabit(habit)) {
            if (!isTimerStarted()) {
                startTimer()
            } else {
                stopTimer(habit)
            }
        } else {
            startTimer()
        }
        setTimerState(isTimerStarted())
    }

    private fun isTimerStarted() = timer!!.state == Timer.TimerState.STARTED

    private fun isActiveHabit(habit: Habit) :Boolean{
        return timer!!.getHabit() != null
                && timer!!.getHabit()!!.id == habit.id
    }

    private fun initTimer(habit: Habit) {
        val application = view.context.applicationContext as HabitApplication
        timer = application.timer
        if (timer!!.getHabit() != null && isActiveHabit(habit)) {
            // if timer is already running, we must subscribe events for this habit
            timer?.subscribe(this)
        }
    }
    private fun setTimerState(started: Boolean){
        if(started){
            startButton.text = "X" // TODO: wymyślić później coś lepszego
            editTimeButton.visibility = View.GONE // TODO: prawdopdobnie coś się tutaj zepsuje (układ)
        } else {
            startButton.text = "P"
            editTimeButton.visibility = View.VISIBLE
        }
    }

    private fun startTimer(){
        timer!!.subscribe(this)
        this.timer?.start(habit!!)
    }

    private fun stopTimer(habit: Habit){
        timer!!.stop()
    }

    override fun updateViewHolder(habit:Habit){
        progressBar.progress = habit.getExecutionsValue()
        updateAmountText(habit, timeText)
    }

    override fun setDone(done:Boolean){
       doneHelper.setDone(done)
    }

    override fun onTick(time: Long) {
        val value = habit!!.goal - time
        updateTime(value)
    }

    override fun onFinish(time: Long) {
        // TODO: to może być dość niepewne, przy wielu widokach może wystepować problem
        addExecution(habit!!, time.toInt())
        setTimerState(false)
        timer!!.unsubscribe(this)
        updateView(habit!!)
    }

    override fun getTimerHabit(): Habit {
        return habit!!
    }

    private fun updateTime(time: Long){
        progressBar.progress = time.toInt()
        updateAmountText(time, habit!!, timeText)
    }
}