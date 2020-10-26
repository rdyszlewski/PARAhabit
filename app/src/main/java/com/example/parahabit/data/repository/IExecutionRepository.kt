package com.example.parahabit.data.repository

import com.example.parahabit.data.models.HabitExecution

interface IExecutionRepository {

    fun insert(execution: HabitExecution): Long
    fun update(execution: HabitExecution)
    fun delete(execution: HabitExecution)
    fun getAll(execution: HabitExecution): List<HabitExecution>
    fun getById(execution: HabitExecution): HabitExecution
    fun getByHabit(habitId: Long): List<HabitExecution>
    fun getByHabitAndDate(habitID: Long, date: Int): MutableList<HabitExecution>
}