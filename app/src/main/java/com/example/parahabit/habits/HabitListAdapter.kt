package com.example.parahabit.habits

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.room.Room
import com.example.parahabit.R
import com.example.parahabit.data.database.AppDatabase
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.models.HabitExecution
import com.example.parahabit.data.models.HabitType
import java.util.*
import kotlin.concurrent.thread

class HabitListAdapter(private val context: Activity, private val habits: Array<Habit>, private val title: String)
    :ArrayAdapter<Habit>(context, R.layout.activity_main, habits){

    private val layoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        val rowView: View?
        val habit = habits[position]
        if(convertView == null){
            val resourceId = getView(habit.type)
            rowView = layoutInflater.inflate(resourceId, parent, false)
            viewHolder = ViewHolder(rowView)
            rowView.tag = viewHolder
        } else {
            rowView = convertView;
            viewHolder = rowView.tag as ViewHolder
        }
        viewHolder.nameText.text = habit.name
        when(habit.type){
            HabitType.TIME->viewHolder.button.setOnClickListener{createExecution(habit, 1)}
            else -> viewHolder.button.setOnClickListener{println("CLick")}
        }
        return rowView!!
    }

    private fun createExecution(habit: Habit, amount: Int){
        val execution = HabitExecution()
        execution.habit = habit.id
        execution.date = Date()
        execution.time = Date()
        execution.amount = amount

        thread {
            val db = Room.databaseBuilder(context, AppDatabase::class.java, "PARAbits").build()
            db.executionDAO().insert(execution)
        }
    }

    private fun getView(type: HabitType): Int{
        return when(type){
            HabitType.TIME->  R.layout.time_habit_item
            else ->  R.layout.habit_item
        }
    }

    private class ViewHolder(view: View?){
        var nameText: TextView = view?.findViewById(R.id.name) as TextView
        var button: Button = view?.findViewById(R.id.done_button) as Button
    }
}