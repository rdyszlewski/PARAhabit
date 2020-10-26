package com.example.parahabit.data.database

import androidx.room.*
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.models.HabitExecution

@Dao
interface IExecutionDAO{

    @Insert
    fun insert(execution: HabitExecution):Long

    @Update
    fun update(execution: HabitExecution)

    @Delete
    fun delete(execution: HabitExecution)

    @Query("SELECT * from executions")
    fun getAll(): List<HabitExecution>

    @Query("SELECT * from executions WHERE  habit =:habit AND date = :date ")
    fun getFromDate(habit: Long, date: Int): MutableList<HabitExecution>

    @Query("SELECT * FROM executions WHERE habit=:habit")
    fun getByHabit(habit: Long):List<HabitExecution>
}