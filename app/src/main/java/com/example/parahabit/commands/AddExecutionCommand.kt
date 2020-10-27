package com.example.parahabit.commands

import android.os.Handler
import android.os.Looper
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.models.HabitExecution
import com.example.parahabit.data.repository.Repository
import java.util.*
import kotlin.concurrent.thread

class AddExecutionCommand(private val habit: Habit, private val amount: Int, private val repository: Repository): ICommand {

    private val callback: CommandCallback<Habit> = CommandCallback()

    fun setCallback(callback: (Habit) -> Unit){
        this.callback.setCallback(callback)
    }

    override fun execute() {
        thread {
            addNewExecution(habit)
        }
    }

    private fun addNewExecution(habit: Habit){
        val execution = createHabitExecution(habit, amount)
        val id = repository.getExecutionRepository().insert(execution)
        execution.id = id
        habit.executions.add(execution)
        callback.execute(habit)
    }

    private fun createHabitExecution(habit: Habit, amount: Int): HabitExecution{
        val execution = HabitExecution()
        execution.habit = habit.id
        execution.date = Date()
        execution.time = Date()
        execution.amount = amount

        return execution
    }


}