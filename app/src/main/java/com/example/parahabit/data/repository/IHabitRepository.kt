package com.example.parahabit.data.repository

import com.example.parahabit.data.models.Habit

interface IHabitRepository {

    fun insert(habit: Habit): Long
    fun update(habit: Habit)
    fun delete(habit: Habit)
    fun getById(id: Long): Habit
    fun getAll(): List<Habit>
}