package com.example.parahabit.habitsList.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.parahabit.habit.activity.HabitActivity
import com.example.parahabit.R
import com.example.parahabit.commands.BulkInsertHabitCommand
import com.example.parahabit.data.database.DatabaseRepository
import com.example.parahabit.data.models.converters.DateConverter
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.models.HabitType
import com.example.parahabit.data.repository.IRepository
import com.example.parahabit.data.repository.Repository
import com.example.parahabit.habitsList.adapter.HabitsAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: HabitsAdapter
    private lateinit var repository: IRepository

    private lateinit var addButton: FloatingActionButton

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

        addButton = findViewById(R.id.addHabitButton)
        addButton.setOnClickListener{openHabitActivity(null)}
    }

    private fun openHabitActivity(habit: Habit?){
        val intent = Intent(applicationContext, HabitActivity::class.java)
        intent.putExtra("habit", habit)
        startActivityForResult(intent, 100) // TODO: dodać tutaj jakąś stałą
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            100 -> {
                if(resultCode == Activity.RESULT_OK){
                    val habit = data?.getParcelableExtra<Habit>("habit")
                    // TODO: można też zrobić w ten sposób, że przekazuje id, i pobiera nawyk o określonym id
                    println(habit?.name)
                }
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