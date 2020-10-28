package com.example.parahabit.habitsList.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit

class QuantitativeHabitsViewHolder(view: View) : HabitsViewHolder(view) {

    private val name = view.findViewById<TextView>(R.id.name)
    private val button = view.findViewById<Button>(R.id.done_button)
    private val done = view.findViewById<TextView>(R.id.executionsAmount)
    private val goal = view.findViewById<TextView>(R.id.goal)

    override fun bind(habit: Habit) {
        this.habit = habit
        name.text = habit.name
        goal.text = habit.goal.toString()

        val sum = habit.executions.sumBy { x -> x.amount }
        done.text = sum.toString()
    }
}