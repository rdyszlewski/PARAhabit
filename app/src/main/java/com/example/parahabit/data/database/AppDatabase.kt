package com.example.parahabit.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.parahabit.data.models.Habit

@Database(entities = [Habit::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun habitDAO(): IHabitDAO;
}