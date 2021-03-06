package com.example.parahabit.commands

import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.repository.Repository
import kotlin.concurrent.thread

class InsertHabitCommand(val habit: Habit,val repository: Repository): ICommand {

    private val callback: CommandCallback<Habit> = CommandCallback()

    fun setCallback(callback: (Habit)->Unit){
        this.callback.setCallback(callback)
    }

    override fun execute() {
        thread {
            repository.getHabitRepository().insert(habit)
            callback.execute(habit)
        }
    }
}