package com.example.parahabit.habitsList.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.parahabit.R
import com.example.parahabit.commands.AddExecutionCommand
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.repository.Repository

class RepetitionsHabitsViewHolder(view: View) : HabitsViewHolder(view) {
    private val name = view.findViewById<TextView>(R.id.name)
    private val button = view.findViewById<Button>(R.id.done_button)
    private val done = view.findViewById<TextView>(R.id.executionsAmount)
    private val goal = view.findViewById<TextView>(R.id.goal)

    override fun bind(habit: Habit) {
        this.habit = habit
        name.text = habit.name
        goal.text = habit.goal.toString()

        updateDoneCount(habit)

        button.setOnClickListener {
            IncreaseExecutions(habit)
        }
    }

    private fun IncreaseExecutions(habit: Habit) {
        val command = AddExecutionCommand(habit, 1, Repository.getInstance())
        command.setCallback { updateDoneCount(habit) }
        command.execute()
    }

    private fun updateDoneCount(habit: Habit) {
        val sum2 = habit.executions.sumBy { x -> x.amount }
        done.text = sum2.toString()
    }
}