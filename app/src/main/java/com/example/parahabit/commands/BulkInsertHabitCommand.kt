package com.example.parahabit.commands

import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.repository.Repository
import kotlin.concurrent.thread

class BulkInsertHabitCommand(val habits: List<Habit>, val repository: Repository) : ICommand  {

    private val callback: CommandCallback<List<Habit>> = CommandCallback()

    fun setCallback(callback: (List<Habit>)->Unit){
        this.callback.setCallback(callback)
    }

    override fun execute() {
        thread {
            for(habit in habits){
                repository.getHabitRepository().insert(habit)
            }
            callback.execute(habits)
        }
    }


}