package com.example.parahabit.habitsList.adapter

import android.app.Activity
import android.view.View
import com.example.parahabit.data.models.HabitType
import com.example.parahabit.timer.Timer
import java.lang.IllegalArgumentException

class HabitsViewHolderFactory {

    companion object {
        fun create(type: HabitType, view: View, context: Activity): HabitsViewHolder {
            return when (type) {
                HabitType.NORMAL -> StandardHabitsViewHolder(view, context)
                HabitType.TIME -> TimeHabitsViewHolder(view, context) // TODO: chyba tak nie powinno być. To powinno raczej pobierać ten licznik ze systemu
                HabitType.REPETITIONS -> RepetitionsHabitsViewHolder(view, context)
                HabitType.QUANTITATIVE -> QuantitativeHabitsViewHolder(view, context)
                else -> throw IllegalArgumentException()
            }
        }
    }
}