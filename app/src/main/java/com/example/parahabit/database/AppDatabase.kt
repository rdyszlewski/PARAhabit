package com.example.parahabit.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.parahabit.data.Habit

@Database(entities = [Habit::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getHabitDAO(): IHabitDAO;
}