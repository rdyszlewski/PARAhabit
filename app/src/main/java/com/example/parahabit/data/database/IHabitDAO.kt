package com.example.parahabit.data.database

import androidx.room.*
import com.example.parahabit.data.models.Habit

@Dao
interface IHabitDAO{
    @Insert
    fun insert(habit: Habit):Long

    @Update
    fun update(habit: Habit)

    @Delete
    fun delete(habit: Habit)

    @Query("SELECT * FROM habits")
    fun getAll():List<Habit>

}