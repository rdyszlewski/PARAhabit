package com.example.parahabit.commands

import android.app.AlertDialog
import android.content.Context
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.repository.Repository
import kotlin.concurrent.thread

class RemoveHabitCommand(val habit: Habit, val repository: Repository, val context: Context): ICommand {

    private var callback: CommandCallback<Habit> = CommandCallback()

    fun setCallback(callback: (Habit)->Unit){
        this.callback.setCallback(callback)
    }

    override fun execute() {
        // TODO: zrobić wyskakiwanie zapytania
        showQuestion()
    }

    private fun showQuestion(){
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Czy na pewno usunąć nawyk?")
                .setPositiveButton(R.string.yes){dialog, which ->
                    removeFromDatabase()
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.no){dialog, which ->
                    dialog.dismiss()
                }
                .show()
    }

    private fun removeFromDatabase(){
        thread {
            repository.getHabitRepository().delete(habit)
            callback.execute(habit)

        }
    }

}