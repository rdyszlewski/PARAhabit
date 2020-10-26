package com.example.parahabit.data.database

import com.example.parahabit.data.models.HabitExecution
import com.example.parahabit.data.repository.IExecutionRepository

class ExecutionRepository(private val db: AppDatabase): IExecutionRepository {

    override fun insert(execution: HabitExecution): Long {
        return db.executionDAO().insert(execution)
    }

    override fun update(execution: HabitExecution) {
        return db.executionDAO().update(execution)
    }

    override fun delete(execution: HabitExecution) {
        return db.executionDAO().delete(execution)
    }

    override fun getAll(execution: HabitExecution): List<HabitExecution> {
        return db.executionDAO().getAll()
    }

    override fun getById(execution: HabitExecution): HabitExecution {
        TODO("Not yet implemented")
    }

    override fun getByHabit(habitId: Long): List<HabitExecution> {
        return db.executionDAO().getByHabit(habitId)
    }

    override fun getByHabitAndDate(habitID: Long, date: Int): MutableList<HabitExecution> {
        return db.executionDAO().getFromDate(habitID, date)
    }
}