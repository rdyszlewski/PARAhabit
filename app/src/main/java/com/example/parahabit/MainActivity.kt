package com.example.parahabit

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.parahabit.commands.BulkInsertHabitCommand
import com.example.parahabit.data.database.DatabaseRepository
import com.example.parahabit.data.models.DateConverter
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.models.HabitType
import com.example.parahabit.data.repository.IRepository
import com.example.parahabit.data.repository.Repository
import com.example.parahabit.habits.adapter.HabitsAdapter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: HabitsAdapter
    private lateinit var repository: IRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initRepository()
//        createHabits()
        loadHabits()
    }

    private fun initRepository() {
        Repository.getInstance().setRepository(DatabaseRepository(applicationContext))
        repository = Repository.getInstance().getRepository()
    }

    private fun initView() {
        val listView: RecyclerView = findViewById(R.id.habit_list)
        adapter = HabitsAdapter(ArrayList())

        registerForContextMenu(listView)
        listView.adapter = adapter
    }

    private fun loadHabits() {
        thread {
            val habits: List<Habit> = repository.getHabitRepository().getAll()
            val dateValue = DateConverter.toNumber(Date())
            for (habit in habits) {
                habit.executions = repository.getExecutionRepository().getByHabitAndDate(habit.id, dateValue)
            }
            Handler(Looper.getMainLooper()).post {
                adapter.updateHabits(habits)
            }
        }
    }

    private fun createHabits() {
        val habit1 = addHabit("Pierwszy", HabitType.NORMAL)
        val habit2 = addHabit("Drugi", HabitType.REPETITIONS)
        val habit3 = addHabit("Trzeci", HabitType.TIME)
        val habit4 = addHabit("Czwarty", HabitType.QUANTITATIVE)
        val command = BulkInsertHabitCommand(listOf(habit1, habit2, habit3, habit4), Repository.getInstance())
        command.setCallback { loadHabits() }
        command.execute()
    }

    private fun addHabit(name: String, type: HabitType): Habit {
        val habit = Habit()
        habit.name = name
        habit.description = "To jest jakiś opis"
        habit.type = type
        if (type == HabitType.REPETITIONS) {
            habit.goal = 15
        }
        return habit
    }
}