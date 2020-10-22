package com.example.parahabit.database

import androidx.room.*
import com.example.parahabit.data.Habit

@Dao
interface IHabitDAO{
    @Insert
    fun insert(habit: Habit)

    @Update
    fun update(habit: Habit)

    @Delete
    fun delete(habit: Habit)

    @Query("SELECT * FROM habits")
    fun getAll():List<Habit>
}