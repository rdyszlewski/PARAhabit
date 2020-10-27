package com.example.parahabit.habits.adapter

import com.example.parahabit.R
import com.example.parahabit.data.models.HabitType
import java.lang.IllegalArgumentException

class HabitsLayoutResourceFactory {
    companion object {
        fun getResource(type: HabitType): Int {
            return when (type) {
                HabitType.NORMAL -> R.layout.habit_item
                HabitType.TIME -> R.layout.time_habit_item
                HabitType.REPETITIONS -> R.layout.repetitions_habit_item
                HabitType.QUANTITATIVE -> R.layout.quantitative_item
                else -> throw IllegalArgumentException()
            }
        }
    }
}