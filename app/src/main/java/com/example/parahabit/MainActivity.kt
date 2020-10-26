package com.example.parahabit

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.database.AppDatabase
import com.example.parahabit.data.database.DatabaseRepository
import com.example.parahabit.data.models.DateConverter
import com.example.parahabit.data.models.HabitExecution
import com.example.parahabit.data.models.HabitType
import com.example.parahabit.data.repository.IRepository
import com.example.parahabit.data.repository.Repository
import com.example.parahabit.habits.HabitsAdapter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private var adapter: HabitsAdapter? = null
    private var repository: IRepository? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Repository.getInstance().setRepository(DatabaseRepository(applicationContext))
        repository = Repository.getInstance().getRepository()
        val listView: RecyclerView = findViewById(R.id.habit_list)
        registerForContextMenu(listView) // TOOD:
        thread {
            createHabits()
            val habits: List<Habit> = repository!!.getHabitRepository().getAll()
            val dateValue = DateConverter.toNumber(Date())
            for (habit in habits) {
                habit.executions = repository!!.getExecutionRepository().getByHabitAndDate(habit.id, dateValue)
            }

            adapter = HabitsAdapter(habits as ArrayList<Habit>)
            listView.adapter = adapter
        }
    }

    private fun createHabits(){
        val id1 = addHabit("Pierwszy", HabitType.NORMAL)
        val id2 = addHabit("Drugi",HabitType.REPETITIONS)
        val id3 = addHabit("Trzeci",HabitType.TIME)
        val id4 =addHabit("Czwarty",HabitType.QUANTITATIVE)

    }

    private fun addHabit(name: String, type: HabitType):Long{
        val habit = Habit()
        habit.name = name
        habit.description = "To jest jakiś opis"
        habit.type = type
        if(type == HabitType.REPETITIONS){
            habit.goal = 15
        }
        return Repository.getInstance().getHabitRepository().insert(habit)
    }


}