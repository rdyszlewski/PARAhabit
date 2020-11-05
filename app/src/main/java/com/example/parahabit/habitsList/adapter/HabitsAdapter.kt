package com.example.parahabit.habitsList.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.models.HabitType
import com.example.parahabit.timer.Timer


class HabitsAdapter(var habits: ArrayList<Habit>, private val context: Activity) : RecyclerView.Adapter<HabitsViewHolder>()  {

    private val filteredList: MutableList<Habit> = ArrayList()
    private var lastFilter: Boolean = false


    fun updateHabits(newHabits: List<Habit>) {
        // TODO: sprawdzić, czy to jest wykorzystywane
        habits.clear()
        habits = newHabits as ArrayList<Habit>
        filter(false)
        updateView()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        println("OnCreateViewHolder")
        val type = HabitType.values()[viewType]
        val resourceId = HabitsLayoutResourceFactory.getResource(type)
        val layoutView = LayoutInflater.from(context).inflate(resourceId, parent, false)
        return HabitsViewHolderFactory.create(type, layoutView, context) // TODO: coś tutaj jest nie tak. Przemysleć jak to rozwiązać
    }

    override fun getItemViewType(position: Int): Int {
        val habit = filteredList[position]
        return habit.type.ordinal
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        println("OnBindViewHolder")
//        holder.bind(habits[position])
        holder.bind(filteredList[position])
    }

    fun updateView() {
        // TODO: tutaj też można zrobić filtrowanie
        notifyDataSetChanged()
    }

    fun addHabit(habit:Habit){
        habits.add(habit)
        filter(lastFilter)
    }

    fun replaceHabit(habit: Habit){
        val index = habits.indexOfFirst { it.id == habit.id }
        habits.set(index, habit)
        filter(lastFilter)
    }

    fun removeHabit(habit: Habit){
        habits.remove(habit)
        filter(lastFilter)
    }

    fun filter(undone: Boolean){
        lastFilter = undone
        filteredList.clear()
        if(undone){
            filteredList.addAll(habits.filter { !it.isFinished() })
        } else {
            filteredList.addAll(habits)
        }
        updateView()
    }

    override fun getItemCount() = filteredList.size
}