package com.example.parahabit.habitsList.adapter

import android.view.View
import com.example.parahabit.data.models.HabitType
import com.example.parahabit.timer.Timer
import java.lang.IllegalArgumentException

class HabitsViewHolderFactory {

    companion object {
        fun create(type: HabitType, view: View): HabitsViewHolder {
            return when (type) {
                HabitType.NORMAL -> StandardHabitsViewHolder(view)
                HabitType.TIME -> TimeHabitsViewHolder(view) // TODO: chyba tak nie powinno być. To powinno raczej pobierać ten licznik ze systemu
                HabitType.REPETITIONS -> RepetitionsHabitsViewHolder(view)
                HabitType.QUANTITATIVE -> QuantitativeHabitsViewHolder(view)
                else -> throw IllegalArgumentException()
            }
        }
    }
}