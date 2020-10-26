package com.example.parahabit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.database.AppDatabase
import com.example.parahabit.data.models.DateConverter
import com.example.parahabit.data.models.HabitExecution
import com.example.parahabit.data.models.HabitType
import com.example.parahabit.habits.HabitsAdapter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    var adapter: HabitsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView: RecyclerView = findViewById(R.id.habit_list)

        thread {
            val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "PARAbits").build()
//            createHabits(db)
            val habits: List<Habit> = db.habitDAO().getAll()
            val dateValue = DateConverter.toNumber(Date())
            for (habit in habits) {
                habit.executions = db.executionDAO().getFromDate(habit.id, dateValue)
            }

            adapter = HabitsAdapter(habits as ArrayList<Habit>)
            listView.adapter = adapter
        }
    }

    private fun createHabits(db: AppDatabase){
        val id1 = addHabit("Pierwszy", HabitType.NORMAL, db)
        val id2 = addHabit("Drugi",HabitType.REPETITIONS, db)
        val id3 = addHabit("Trzeci",HabitType.TIME, db)
        val id4 =addHabit("Czwarty",HabitType.QUANTITATIVE, db)

        val execution = HabitExecution()
        execution.habit = id2 // TODO: przerobić id na long
        execution.amount = 3
        execution.date = Date()
        execution.time = Date()
        db.executionDAO().insert(execution)
    }

    private fun addHabit(name: String, type: HabitType, db: AppDatabase):Long{
        val habit = Habit()
        habit.name = name
        habit.description = "To jest jakiś opis"
        habit.type = type
        if(type == HabitType.REPETITIONS){
            habit.goal = 15
        }
        return db.habitDAO().insert(habit)
    }


}