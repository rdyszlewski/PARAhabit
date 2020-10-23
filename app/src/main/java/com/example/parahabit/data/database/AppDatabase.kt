package com.example.parahabit.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.models.HabitExecution

@Database(entities = [Habit::class, HabitExecution::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun habitDAO(): IHabitDAO
    abstract fun executionDAO(): IExecutionDAO
}