package com.example.parahabit.habitsList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.models.HabitType
import com.example.parahabit.timer.Timer


class HabitsAdapter(var habits: ArrayList<Habit>) : RecyclerView.Adapter<HabitsViewHolder>() {

    fun updateHabits(newHabits: List<Habit>) {
        habits.clear()
        habits = newHabits as ArrayList<Habit>
        updateView()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {

        val type = HabitType.values()[viewType]
        val resourceId = HabitsLayoutResourceFactory.getResource(type)
        val layoutView = LayoutInflater.from(parent.context).inflate(resourceId, parent, false)
        return HabitsViewHolderFactory.create(type, layoutView) // TODO: coś tutaj jest nie tak. Przemysleć jak to rozwiązać
    }

    override fun getItemViewType(position: Int): Int {
        val habit = habits[position]
        return habit.type.ordinal
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    fun updateView() {
        notifyDataSetChanged()
    }

    override fun getItemCount() = habits.size
}