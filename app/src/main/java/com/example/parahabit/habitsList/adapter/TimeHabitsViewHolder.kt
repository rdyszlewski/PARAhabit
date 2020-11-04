package com.example.parahabit.habitsList.adapter

import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.parahabit.HabitApplication
import com.example.parahabit.R
import com.example.parahabit.commands.AddExecutionCommand
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.repository.Repository
import com.example.parahabit.timer.ITimerCallback
import com.example.parahabit.timer.Timer
import com.google.android.material.button.MaterialButton

// TODO: zz godzinami prawdopodobnie będzie trzeba zrobić jakiś przelicznik
class TimeHabitsViewHolder(private val view: View) : HabitsViewHolder(view), ITimerCallback {

    private val nameText = view.findViewById<TextView>(R.id.name)
    private val doneText = view.findViewById<TextView>(R.id.done_text)
    private val startButton = view.findViewById<MaterialButton>(R.id.start_button)
    private val editTimeButton = view.findViewById<MaterialButton>(R.id.add_time_button)
    private val menuButton = view.findViewById<MaterialButton>(R.id.menu_button)
    private val progressBar = view.findViewById<ProgressBar>(R.id.progress)
    private val timeText = view.findViewById<TextView>(R.id.time_text)

    private var timer: Timer? = null

    override fun bind(habit: Habit) {
        this.habit = habit
        nameText.text = habit.name
        startButton.setOnClickListener{
            println("Bindowanko")
            val application = view.context.applicationContext as HabitApplication
            timer = application.timer
            // TODO: to powinno być

            // TODO: coś mi isę wydaje, że może być problem z ponownym połączeniem tego po zmianie aplikacji

            // TODO: tutaj powinno być jeszcze sprawdzenie, czy habit jest odpowiedni

            if(timer!!.getHabit() == habit){
                if(timer!!.state != Timer.TimerState.STARTED){
//                    timer!!.setHabit(habit)
                    timer!!.subscribe(this)
                    startTimer()
                } else {
                    stopTimer(habit) // TODO: czy to jest potrzebne
                }
            } else {
//                timer!!.setHabit(habit)
                timer!!.subscribe(this)
                startTimer()
            }

            setTimerState(timer!!.state == Timer.TimerState.STARTED)

        }
        progressBar.max = habit.goal
        updateView(habit)
        setDone(habit.isFinished())

    }

    private fun updateTimeText(habit:Habit){
        // TODO: wprowadzić placeholders
        timeText.text = habit.getExecutionsValue().toString() + "/" + habit.goal
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
        println("start timer")
        this.timer?.start(habit!!)
    }

    private fun stopTimer(habit: Habit){
        timer!!.stop()
    }

    private fun updateView(habit:Habit){
        progressBar.progress = habit.getExecutionsValue()
        updateTimeText(habit)
        setDone(habit.isFinished())
    }

    private fun setDone(done:Boolean){
        if(done){
            doneText.visibility = View.VISIBLE
            startButton.visibility = View.INVISIBLE // TODO: sprawdzić, jak to będzie wyglądało z napisaem ukończone
            editTimeButton.visibility = View.INVISIBLE
            view.setBackgroundColor(Color.GREEN)
        } else {
            doneText.visibility = View.GONE
            startButton.visibility = View.VISIBLE
            editTimeButton.visibility = View.VISIBLE
            view.setBackgroundColor(Color.WHITE)
        }
    }

    override fun onTick(time: Long) {
        val value = habit!!.goal - time
        updateTime(value)
    }

    override fun onFinish() {
        println("Zakończyłem zadanie")
        setTimerState(false)
        timer!!.unsubscribe(this)

    }

    override fun getTimerHabit(): Habit {
        return habit!!
    }

    private fun updateTime(time: Long){
        progressBar.progress = time.toInt()
        // TODO: zmienić zasób napisu, tak, żeby korzystał z placeholders
        timeText.text = time.toString() + "/" + habit!!.goal
    }
}