package com.example.parahabit.data.database

import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.repository.IHabitRepository

class HabitRepository(private val db: AppDatabase): IHabitRepository {

    override fun insert(habit: Habit): Long {
        return db.habitDAO().insert(habit)
    }

    override fun update(habit: Habit) {
        return db.habitDAO().update(habit)
    }

    override fun delete(habit: Habit) {
        return db.habitDAO().delete(habit)
    }

    override fun getById(id: Long): Habit {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Habit> {
        return db.habitDAO().getAll()
    }

}