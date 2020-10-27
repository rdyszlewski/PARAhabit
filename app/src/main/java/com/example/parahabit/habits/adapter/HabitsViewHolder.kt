package com.example.parahabit.habits.adapter

import android.content.Intent
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.parahabit.HabitExecutionsActivity
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit

abstract class HabitsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {
            if (habit != null) {
                openExecutions(habit!!)
            }
        }
        view.setOnLongClickListener {
            openMenu(habit!!)
            true
        }
    }

    protected var habit: Habit? = null

    abstract fun bind(habit: Habit)

    private fun openExecutions(habit: Habit) {
        val intent = Intent(itemView.context, HabitExecutionsActivity::class.java)
        intent.putExtra("habit", habit.id)
        itemView.context.startActivity(intent)
    }

    private fun openMenu(habit: Habit) {
        val popupMenu = PopupMenu(itemView.context, itemView)
        popupMenu.menuInflater.inflate(R.menu.habit_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.remove -> println("Usuwanie")
                R.id.edit -> println("Edytowanie")
                R.id.edit_executions -> println("Też edytowanie")
            }
            true
        })
        popupMenu.show()
    }
}