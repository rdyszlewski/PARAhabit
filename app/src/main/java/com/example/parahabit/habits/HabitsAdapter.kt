package com.example.parahabit.habits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.models.HabitType


class HabitsAdapter(var habits: ArrayList<Habit>): RecyclerView.Adapter<HabitsAdapter.HabitsViewHolder>() {

    fun updateHabits(newHabits: List<Habit>){
        habits.clear()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        // TODO: przeprowadzić tutaj jakąś refaktoryzację
        println(viewType)
        when(viewType) {
            HabitType.NORMAL.ordinal -> {
                println("Zwracam normal")
                val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)
                return NormalHabitsViewHolder(view)
            }
            HabitType.TIME.ordinal-> {
                println("Zwracam time")
                val view = LayoutInflater.from(parent.context).inflate(R.layout.time_habit_item, parent, false)
                return TimeHabitsViewHolder(view)
            }
            HabitType.QUANTITATIVE.ordinal->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.quantitative_item, parent, false)
                return QuantitativeViewHolder(view)
            }
        }

        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)
        return NormalHabitsViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val habit = habits[position]
        return habit.type.ordinal
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    override fun getItemCount() = habits.size


    abstract class  HabitsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener{
                println("Kliknięto na element")
            }
        }

        abstract fun bind(habit: Habit)
    }

    class NormalHabitsViewHolder(view: View): HabitsViewHolder(view){

        private val name = view.findViewById(R.id.name) as TextView
        private val button = view.findViewById(R.id.done_button) as Button

        override fun bind(habit: Habit){
            name.text = habit.name
            button.setOnClickListener{
                println("CLICK")
            }
        }
    }

    class TimeHabitsViewHolder(view: View): HabitsViewHolder(view){
        private val name = view.findViewById(R.id.name) as TextView
        private val button = view.findViewById(R.id.done_button) as Button

        override fun bind(habit: Habit){
            name.text = habit.name
            button.setOnClickListener{
                println("Kliknięto na czas")
            }
        }
    }

    class QuantitativeViewHolder(view: View): HabitsViewHolder(view){

        private val name = view.findViewById<TextView>(R.id.name)
        private val button = view.findViewById<Button>(R.id.done_button)
        private val done = view.findViewById<TextView>(R.id.executionsAmount)
        private val goal = view.findViewById<TextView>(R.id.goal)

        override fun bind(habit: Habit) {
            name.text = habit.name
            goal.text = habit.goal.toString()

            val sum = habit.executions.sumBy { x->x.amount }
            done.text = sum.toString()

            // TODO: zrobić coś z przyciskiem
        }
    }
}