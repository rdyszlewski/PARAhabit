package com.example.parahabit.data.database

import androidx.room.*
import com.example.parahabit.data.models.HabitExecution

@Dao
interface IExecutionDAO{

    @Insert
    fun insert(execution: HabitExecution)

    @Update
    fun update(execution: HabitExecution)

    @Delete
    fun delete(execution: HabitExecution)

    @Query("SELECT * from executions")
    fun getAll(): List<HabitExecution>

    @Query("SELECT * from executions WHERE date = :date")
    fun getFromDate(date: Int): List<HabitExecution>
}