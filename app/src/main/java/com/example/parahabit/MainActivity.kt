package com.example.parahabit

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.room.Room
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.database.AppDatabase
import com.example.parahabit.data.models.HabitExecution
import com.example.parahabit.data.models.HabitType
import com.example.parahabit.habits.HabitListAdapter
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    var adapter: HabitListAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView: ListView = findViewById(R.id.habit_list)
        thread {
            val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "PARAbits").build()
//            createHabits(db)
            val habits: List<Habit> = db.habitDAO().getAll()
            println(habits)
            Log.v("siema", habits.size.toString())

            adapter = HabitListAdapter(this, habits.toTypedArray(), "Habbits")
            listView.adapter = adapter

            val executions = db.executionDAO().getAll()
            println("Executions " + executions.size)
        }
    }

    private fun createHabits(db: AppDatabase){
        addHabit("Pierwszy", HabitType.NORMAL, db)
        addHabit("Drugi",HabitType.TIME, db)
        addHabit("Trzeci",HabitType.TIME, db)
        addHabit("Czwarty",HabitType.NORMAL, db)
    }

    private fun addHabit(name: String, type: HabitType, db: AppDatabase){
        val habit = Habit()
        habit.name = name
        habit.description = "To jest jakiś opis"
        habit.type = type;
        db.habitDAO().insert(habit)
    }


}