package com.example.parahabit.habitsList.adapter

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.PopupMenu
import com.example.parahabit.R
import com.example.parahabit.commands.RemoveHabitCommand
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.repository.Repository
import com.example.parahabit.habit.activity.HabitActivity
import com.example.parahabit.habitsList.activity.MainActivity

class HabitItemMenu(val itemView: View, val context: Activity) {

    fun openMenu(habit: Habit) {
        val popupMenu = PopupMenu(itemView.context, itemView)
        popupMenu.menuInflater.inflate(R.menu.habit_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.remove -> remove(habit)
                R.id.edit -> edit(habit)
                R.id.edit_executions -> println("Też edytowanie")
            }
            true
        }
        popupMenu.show()
    }

    private fun remove(habit: Habit){
        val command = RemoveHabitCommand(habit, Repository.getInstance(), context)
        command.setCallback { removedHabit->(context as MainActivity).removeHabit(removedHabit) }
        command.execute()
    }

    private fun edit(habit: Habit){
        val intent = Intent(context, HabitActivity::class.java)
        intent.putExtra("habit", habit)
        (context as MainActivity).startActivityForResult(intent, 200)
    }

}