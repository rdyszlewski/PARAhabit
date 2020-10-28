package com.example.parahabit.habitsList.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit

class NormalHabitsViewHolder(view: View) : HabitsViewHolder(view) {

    private val name = view.findViewById(R.id.name) as TextView
    private val button = view.findViewById(R.id.done_button) as Button

    override fun bind(habit: Habit) {
        this.habit = habit
        name.text = habit.name
        button.setOnClickListener {
        }
    }
}