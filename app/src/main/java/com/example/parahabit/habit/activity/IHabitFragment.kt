package com.example.parahabit.habit.activity

import com.example.parahabit.data.models.Habit

interface IHabitFragment {
    fun setupHabit(habit: Habit)
}