package com.example.parahabit.habitsList.adapter

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.parahabit.HabitExecutionsActivity
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit
import com.example.parahabit.habit.activity.HabitActivity
import com.example.parahabit.habitsList.activity.MainActivity

abstract class HabitsViewHolder(protected val view: View, protected val context: Activity) : RecyclerView.ViewHolder(view){

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

    //

    private fun openMenu(habit: Habit) {
        val popupMenu = PopupMenu(itemView.context, itemView)
        popupMenu.menuInflater.inflate(R.menu.habit_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.remove -> println("Usuwanie")
                R.id.edit -> edit(habit)
                R.id.edit_executions -> println("Też edytowanie")
            }
            true
        })
        popupMenu.show()
    }

    private fun remove(habit: Habit){

    }

    private fun edit(habit: Habit){
        // TODO: nie można wystartować z wynikiem :( Trzeba to będzie jakoś zrobić inaczej
        val intent = Intent(view.context, HabitActivity::class.java)
        intent.putExtra("habit", habit)
        // TODO: teraz nie wiem, jak to zrobić. Może to powinno być w jakimś innym miejsccu
        (context as MainActivity).startActivityForResult(intent, 200)
//        view.context.startActivity(intent) // TODO: tutaj powinno być uruchomienie aktywności z otrzymaniem wyniku
    }


}