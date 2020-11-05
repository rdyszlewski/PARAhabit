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

    protected fun updateAmountText(habit:Habit, view: TextView){
        val amount = habit.getExecutionsValue()
        updateAmountText(amount.toLong(), habit, view)
    }

    protected fun updateAmountText(amount: Long, habit:Habit, view: TextView){
        val text = "${amount}/${habit.goal}"
        view.text = text
    }

    protected fun updateView(habit:Habit){
        updateViewHolder(habit)
        setDone(habit.isFinished())
    }

    private fun openMenu(habit: Habit) {
        val popupMenu = PopupMenu(itemView.context, itemView)
        popupMenu.menuInflater.inflate(R.menu.habit_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.remove -> remove(habit)
                R.id.edit -> edit(habit)
                R.id.edit_executions -> println("Też edytowanie")
            }
            true
        })
        popupMenu.show()
    }

    private fun remove(habit: Habit){
        val command = RemoveHabitCommand(habit, Repository.getInstance(), context)
        command.setCallback { habit->(context as MainActivity).removeHabit(habit) }
        command.execute()
    }

    private fun edit(habit: Habit){
        // TODO: nie można wystartować z wynikiem :( Trzeba to będzie jakoś zrobić inaczej
        val intent = Intent(view.context, HabitActivity::class.java)
        intent.putExtra("habit", habit)
        // TODO: teraz nie wiem, jak to zrobić. Może to powinno być w jakimś innym miejsccu
        (context as MainActivity).startActivityForResult(intent, 200)
    }
}