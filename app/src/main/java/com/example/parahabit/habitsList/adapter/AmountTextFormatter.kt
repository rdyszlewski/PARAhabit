package com.example.parahabit.habitsList.adapter

import android.widget.TextView
import com.example.parahabit.data.models.Habit

class AmountTextFormatter {
    companion object{

        fun updateText(habit: Habit, view: TextView){
            val amount = habit.getExecutionsValue()
            updateText(amount.toLong(), habit, view)
        }

        fun updateText(amount: Long, habit: Habit, view: TextView){
            val text = "${amount}/${habit.goal}"
            view.text = text
        }
    }
}