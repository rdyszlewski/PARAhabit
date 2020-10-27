package com.example.parahabit.habits.adapter

import android.view.View
import com.example.parahabit.data.models.HabitType
import java.lang.IllegalArgumentException

class HabitsViewHolderFactory {

    companion object {
        fun create(type: HabitType, view: View): HabitsViewHolder {
            return when (type) {
                HabitType.NORMAL -> NormalHabitsViewHolder(view)
                HabitType.TIME -> TimeHabitsViewHolder(view)
                HabitType.REPETITIONS -> RepetitionsHabitsViewHolder(view)
                HabitType.QUANTITATIVE -> QuantitativeHabitsViewHolder(view)
                else -> throw IllegalArgumentException()
            }
        }
    }
}