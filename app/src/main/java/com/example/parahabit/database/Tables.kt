package com.example.parahabit.database

import android.provider.BaseColumns

object Tables{
    object HabitTable: BaseColumns{
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
        const val HABIT = "habit_id"
        const val DAY = "day"
        // TODO: tutaj można rozwiązać sprawę 
    }

    object HabitExecution: BaseColumns{
        const val HABIT = "habit_id"
        const val AMOUNT = "amount"
    }
}