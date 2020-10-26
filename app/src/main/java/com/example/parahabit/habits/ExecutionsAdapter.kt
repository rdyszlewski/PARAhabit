package com.example.parahabit.habits

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parahabit.R
import com.example.parahabit.data.models.HabitExecution

class ExecutionsAdapter(var executions: ArrayList<HabitExecution>){

    class ExecutionsViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val habit = view.findViewById(R.id.habitName) as TextView
        private val date = view.findViewById(R.id.executionDate) as TextView

        fun bind(execution: HabitExecution){
            // TODO: sprawdzić, jak zrobić, żeby przy execution pobierał się też cały Habit
            habit.text = execution.habit.toString()
        }
    }
}