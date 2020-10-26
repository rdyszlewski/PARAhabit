package com.example.parahabit.habits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parahabit.R
import com.example.parahabit.data.models.HabitExecution

class ExecutionsAdapter(var executions: ArrayList<HabitExecution>): RecyclerView.Adapter<ExecutionsAdapter.ExecutionsViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExecutionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.execution_item, parent, false)
        return ExecutionsViewHolder(view)
    }

    override fun getItemCount() = executions.size

    override fun onBindViewHolder(holder: ExecutionsViewHolder, position: Int) {
        holder.bind(executions[position])
    }

    class ExecutionsViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val habit = view.findViewById(R.id.habitName) as TextView
        private val date = view.findViewById(R.id.executionDate) as TextView

        fun bind(execution: HabitExecution){
            // TODO: sprawdzić, jak zrobić, żeby przy execution pobierał się też cały Habit
            habit.text = execution.habit.toString()
            date.text = execution.date.toString()
        }
    }
}