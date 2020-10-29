package com.example.parahabit.habitsList.adapter

import android.graphics.Color
import android.opengl.Visibility
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.parahabit.R
import com.example.parahabit.commands.AddExecutionCommand
import com.example.parahabit.commands.SaveHabitCommand
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.repository.Repository
import com.google.android.material.button.MaterialButton

class StandardHabitsViewHolder(private val view: View) : HabitsViewHolder(view) {

    private val name = view.findViewById<TextView>(R.id.name)
    private val doneText = view.findViewById<TextView>(R.id.done_text)
    private val doneButton = view.findViewById<MaterialButton>(R.id.done_button)
    private val menuButton = view.findViewById<MaterialButton>(R.id.menu_button)

    override fun bind(habit: Habit) {
        this.habit = habit
        setDone(habit.isFinished())
        name.text = habit.name
        doneButton.setOnClickListener {
            doneHabit(habit)
        }

        // TODO: zaimplementować menu
    }

    private fun doneHabit(habit:Habit){
        println(habit.executions.size)
        println(habit.goal)
        val command = AddExecutionCommand(habit, 1, Repository.getInstance())
        command.setCallback { updatedHabit->updateView(updatedHabit) }
        command.execute()
    }

    private fun updateView(habit:Habit){
        setDone(habit.isFinished())
    }

    private fun setDone(done:Boolean){
        if(done){
            doneText.visibility = View.VISIBLE
            doneButton.visibility = View.GONE
            view.setBackgroundColor(Color.GREEN)
        } else {
            doneText.visibility = View.GONE
            doneButton.visibility = View.VISIBLE
            view.setBackgroundColor(Color.WHITE) // TODO: być może będzie trzeba zachować kolor
        }
    }

}