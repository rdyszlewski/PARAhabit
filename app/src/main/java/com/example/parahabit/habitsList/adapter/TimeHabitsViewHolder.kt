package com.example.parahabit.habitsList.adapter

import android.app.Activity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.parahabit.HabitApplication
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit
import com.example.parahabit.timer.ITimerCallback
import com.example.parahabit.timer.Timer
import com.example.parahabit.timer.TimerState
import com.google.android.material.button.MaterialButton

// TODO: zz godzinami prawdopodobnie będzie trzeba zrobić jakiś przelicznik
class TimeHabitsViewHolder(view: View, context: Activity) : HabitsViewHolder(view, context) {

    private val nameText = view.findViewById<TextView>(R.id.name)
    private val doneText = view.findViewById<TextView>(R.id.done_text)
    private val startButton = view.findViewById<MaterialButton>(R.id.start_button)
    private val editTimeButton = view.findViewById<MaterialButton>(R.id.add_time_button)
    private val menuButton = view.findViewById<MaterialButton>(R.id.menu_button)
    private val progressBar = view.findViewById<ProgressBar>(R.id.progress)
    private val timeText = view.findViewById<TextView>(R.id.time_text)

    private val doneHelper = DoneViewHelper.Builder(view).setDoneText(doneText)
            .setInvisibleViews(listOf(startButton, editTimeButton)).build()

    private val timerControl: TimerControl = TimerControl()


    override fun bind(habit: Habit) {
        timerControl.init(habit)
        this.habit = habit
        nameText.text = habit.name
        progressBar.max = habit.goal
        updateView(habit)
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

    override fun updateViewHolder(habit:Habit){
        progressBar.progress = habit.getExecutionsValue()
        AmountTextFormatter.updateText(habit, timeText)
    }

    override fun setDone(done:Boolean){
       doneHelper.setDone(done)
    }

    private fun updateTime(time: Long){
        progressBar.progress = time.toInt()
        AmountTextFormatter.updateText(time, habit!!, timeText)
    }

    inner class TimerControl: ITimerCallback{

        private var timer: Timer? = null

        fun init(habit:Habit){
            if(timer == null){
                initTimer(habit)
            }
            startButton.setOnClickListener {
                onTimerClick(habit)
            }
        }

        private fun initTimer(habit: Habit) {
            val application = view.context.applicationContext as HabitApplication
            timer = application.timer
            if (timer!!.isStarted() && timer!!.isActiveTimer(getTimerId())) {
                // if timer is already running, we must subscribe events for this habit
                timer?.subscribe(this)
            }
        }


        private fun onTimerClick(habit: Habit) {
            // TODO: posprawdzać jeszcze to
            if (timer!!.isActiveTimer(getTimerId())) {
                if (!timer!!.isStarted()) {
                    startTimer()
                } else {
                    stopTimer(habit)
                }
            } else {
                startTimer()
            }
            setTimerState(timer!!.isStarted())
        }

        private fun startTimer(){
            timer!!.subscribe(this)
            val time = habit!!.goal - habit!!.getExecutionsValue()
            this.timer?.start(habit!!.id, time.toLong())
        }

        private fun stopTimer(habit: Habit){
            timer!!.stop()
            timer!!.unsubscribe(this)
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

        override fun getTimerId(): Long {
            return habit!!.id
        }
    }
}