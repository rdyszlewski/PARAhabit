package com.example.parahabit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.parahabit.data.database.AppDatabase
import com.example.parahabit.data.models.HabitExecution
import com.example.parahabit.habit.ExecutionsAdapter
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class HabitExecutionsActivity : AppCompatActivity() {

    private var list: RecyclerView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_executions)

        list = findViewById(R.id.executionsList)

        val habitId = intent.getLongExtra("habit", -1)
        thread {
            val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "PARAbits").build()
            val executions = db.executionDAO().getByHabit(habitId)
            val adapter = ExecutionsAdapter(executions as ArrayList<HabitExecution>)
            list?.adapter = adapter
        }
    }
}