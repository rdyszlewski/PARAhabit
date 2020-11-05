package com.example.parahabit.habitsList.adapter

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parahabit.HabitExecutionsActivity
import com.example.parahabit.R
import com.example.parahabit.commands.AddExecutionCommand
import com.example.parahabit.commands.RemoveHabitCommand
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.repository.Repository
import com.example.parahabit.habit.activity.HabitActivity
import com.example.parahabit.habitsList.activity.MainActivity

abstract class HabitsViewHolder(protected val view: View, protected val context: Activity) : RecyclerView.ViewHolder(view){

    protected val menu: HabitItemMenu = HabitItemMenu(view, context)

    init {
        view.setOnClickListener {
            if (habit != null) {
                openExecutions(habit!!)
            }
        }
        view.setOnLongClickListener {
            menu.openMenu(habit!!)
            true
        }
    }

    protected var habit: Habit? = null

    abstract fun bind(habit: Habit)
    protected abstract fun updateViewHolder(habit: Habit)
    protected abstract fun setDone(done: Boolean)

    private fun openExecutions(habit: Habit) {
        val intent = Intent(context, HabitExecutionsActivity::class.java)
        intent.putExtra("habit", habit.id)
        context.startActivity(intent)
    }

    protected fun addExecution(habit:Habit, amount: Int){
        val command = AddExecutionCommand(habit, amount, Repository.getInstance())
        command.setCallback { updatedHabit->updateView(updatedHabit) }
        command.execute()
    }

    protected fun updateView(habit:Habit){
        updateViewHolder(habit)
        setDone(habit.isFinished())
    }

}