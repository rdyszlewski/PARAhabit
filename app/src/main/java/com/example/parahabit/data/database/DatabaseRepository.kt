package com.example.parahabit.data.database

import android.content.Context
import androidx.room.Room
import com.example.parahabit.data.repository.IExecutionRepository
import com.example.parahabit.data.repository.IHabitRepository
import com.example.parahabit.data.repository.IRepository

class DatabaseRepository(context: Context) : IRepository {

    private val db: AppDatabase;

    private val habitRepository: IHabitRepository
    private val executionRepository: IExecutionRepository

    init {
        db = Room.databaseBuilder(context, AppDatabase::class.java, "PARAbits").build()
        habitRepository = HabitRepository(db)
        executionRepository = ExecutionRepository(db)
    }

    override fun getHabitRepository():IHabitRepository {
        return habitRepository
    }

    override fun getExecutionRepository(): IExecutionRepository {
        return executionRepository
    }
}