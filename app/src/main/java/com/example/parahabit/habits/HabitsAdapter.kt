package com.example.parahabit.habits

import android.app.Application
import android.content.Intent
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.text.style.UpdateLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.parahabit.HabitExecutionsActivity
import com.example.parahabit.MainActivity
import com.example.parahabit.R
import com.example.parahabit.data.database.AppDatabase
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.models.HabitExecution
import com.example.parahabit.data.models.HabitType
import com.example.parahabit.data.repository.Repository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


class HabitsAdapter(var habits: ArrayList<Habit>) : RecyclerView.Adapter<HabitsAdapter.HabitsViewHolder>() {

    fun updateHabits(newHabits: List<Habit>) {
        habits.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {

        val type = HabitType.values()[viewType]
        val resourceId = HabitsLayoutResourceFactory.getResource(type)
        val layoutView = LayoutInflater.from(parent.context).inflate(resourceId, parent, false)
        return HabitsViewHolderFactory.create(type, layoutView)
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

    class HabitsViewHolderFactory {

        companion object {
            fun create(type: HabitType, view: View): HabitsViewHolder {
                return when (type) {
                    HabitType.NORMAL -> NormalHabitsViewHolder(view)
                    HabitType.TIME -> TimeHabitsViewHolder(view)
                    HabitType.REPETITIONS -> RepetitionsHabitsViewHolder(view)
                    HabitType.QUANTITATIVE -> QuantativeHabitsViewHolder(view)
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }

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

    abstract class HabitsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                if (habit != null) {
                    openExecutions(habit!!)
                }
            }
            view.setOnLongClickListener {
                openMenu(habit!!)
                true
            }
        }

        protected var habit: Habit? = null

        abstract fun bind(habit: Habit)

        private fun openExecutions(habit: Habit) {
            val intent = Intent(itemView.context, HabitExecutionsActivity::class.java)
            intent.putExtra("habit", habit.id)
            itemView.context.startActivity(intent)
        }

        private fun openMenu(habit: Habit){
            val popupMenu = PopupMenu(itemView.context, itemView)
            popupMenu.menuInflater.inflate(R.menu.habit_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener ( PopupMenu.OnMenuItemClickListener { item->
                when(item.itemId){
                    R.id.remove-> println("Usuwanie")
                    R.id.edit->println("Edytowanie")
                    R.id.edit_executions->println("Też edytowanie")
                }
                true
            })
            popupMenu.show()
        }
    }

    class NormalHabitsViewHolder(view: View) : HabitsViewHolder(view) {

        private val name = view.findViewById(R.id.name) as TextView
        private val button = view.findViewById(R.id.done_button) as Button

        override fun bind(habit: Habit) {
            this.habit = habit
            name.text = habit.name
            button.setOnClickListener {
            }
        }
    }

    class TimeHabitsViewHolder(view: View) : HabitsViewHolder(view) {
        private val name = view.findViewById(R.id.name) as TextView
        private val button = view.findViewById(R.id.done_button) as Button

        override fun bind(habit: Habit) {
            this.habit = habit
            name.text = habit.name
            button.setOnClickListener {
                println("Kliknięto na czas")
            }
        }
    }

    class QuantativeHabitsViewHolder(view: View) : HabitsViewHolder(view) {

        private val name = view.findViewById<TextView>(R.id.name)
        private val button = view.findViewById<Button>(R.id.done_button)
        private val done = view.findViewById<TextView>(R.id.executionsAmount)
        private val goal = view.findViewById<TextView>(R.id.goal)

        override fun bind(habit: Habit) {
            this.habit = habit
            name.text = habit.name
            goal.text = habit.goal.toString()

            val sum = habit.executions.sumBy { x -> x.amount }
            done.text = sum.toString()
        }
    }

    class RepetitionsHabitsViewHolder(view: View) : HabitsViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.name)
        private val button = view.findViewById<Button>(R.id.done_button)
        private val done = view.findViewById<TextView>(R.id.executionsAmount)
        private val goal = view.findViewById<TextView>(R.id.goal)

        private val mainLooper = Looper.getMainLooper()
        override fun bind(habit: Habit) {
            this.habit = habit
            name.text = habit.name
            goal.text = habit.goal.toString()

            updateDoneCount(habit)
            button.setOnClickListener {
                thread {
                    addNewExecution(habit)
                }
            }
        }

        private fun addNewExecution(habit: Habit) {
            val execution = createHabitExecution(habit, 1)
            val id = Repository.getInstance().getExecutionRepository().insert(execution)
            execution.id = id
            habit.executions.add(execution)

            Handler(mainLooper).post {
                updateDoneCount(habit)
            }
        }

        private fun updateDoneCount(habit: Habit) {
            val sum2 = habit.executions.sumBy { x -> x.amount }
            done.text = sum2.toString()
        }

        private fun createHabitExecution(habit: Habit, amount: Int): HabitExecution {
            val execution = HabitExecution()
            execution.habit = habit.id
            execution.date = Date()
            execution.time = Date()
            execution.amount = amount

            return execution
        }
    }
}