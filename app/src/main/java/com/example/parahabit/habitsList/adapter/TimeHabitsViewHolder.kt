package com.example.parahabit.habitsList.adapter

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.parahabit.R
import com.example.parahabit.commands.AddExecutionCommand
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.repository.Repository
import com.example.parahabit.timer.Timer
import com.google.android.material.button.MaterialButton

// TODO: zz godzinami prawdopodobnie będzie trzeba zrobić jakiś przelicznik
class TimeHabitsViewHolder(private val view: View, private val timer: Timer) : HabitsViewHolder(view) {

    private val nameText = view.findViewById<TextView>(R.id.name)
    private val doneText = view.findViewById<TextView>(R.id.done_text)
    private val startButton = view.findViewById<MaterialButton>(R.id.start_button)
    private val editTimeButton = view.findViewById<MaterialButton>(R.id.add_time_button)
    private val menuButton = view.findViewById<MaterialButton>(R.id.menu_button)
    private val progressBar = view.findViewById<ProgressBar>(R.id.progress)
    private val timeText = view.findViewById<TextView>(R.id.time_text)

    override fun bind(habit: Habit) {
        this.habit = habit
        nameText.text = habit.name
        startButton.setOnClickListener{
            if(timer.started){
                stopTimer(habit)
            } else {
                startTimer()
            }
            setTimerState(timer.started)
        }
        progressBar.max = habit.goal
        updateTimeText(habit)
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
        timer.start()
        // TODO: rozpoczęcie odliczania
    }

    private fun stopTimer(habit: Habit){
        timer.stop()
        val time = timer.getTime()

        val command = AddExecutionCommand(habit, time, Repository.getInstance())
        command.setCallback { updatedHabit -> updateView(updatedHabit) }
        command.execute()
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
}