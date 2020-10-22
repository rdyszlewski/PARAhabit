package com.example.parahabit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.room.Room
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.database.AppDatabase
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
//            addHabit("Pierwszy", db)
//            addHabit("Drugi", db)
//            addHabit("Trzeci", db)
//            addHabit("Czwarty", db)
            val habits: List<Habit> = db.habitDAO().getAll()
            println(habits)
            Log.v("siema", habits.size.toString())

            adapter = HabitListAdapter(this, habits.toTypedArray(), "Habbits")
            listView.adapter = adapter
        }
    }

    private fun addHabit(name: String, db: AppDatabase){
        val habit: Habit = Habit()
        habit.name = name
        habit.description = "To jest jakiś opis"
        db.habitDAO().insert(habit)
    }


}