package com.example.parahabit.database

import android.provider.BaseColumns

object Tables{
    object Habit: BaseColumns{
        const val TABLE_NAME = "habits"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val PERIOD = "period"
        const val GOAL = "goal"
        const val UNIT = "unit"
    }

    object Period: BaseColumns{
        const val TABLE_NAME = "periods"
        const val NAME = "name"
    }

    object Units: BaseColumns{
        const val TABLE_NAME = "units"
        const val NAME = "name"
    }

    object Repetitions: BaseColumns{
        const val TABLE_NAME = "repetitions"
        const val HABIT = "habit_id"
        const val DAY = "day"
        // TODO: tutaj można rozwiązać sprawę 
    }

    object HabitExecution: BaseColumns{
        const val TABLE_NAME = "habit_executions"
        const val HABIT = "habit_id"
        const val DATE ="date"
        const val AMOUNT = "amount"
    }
}