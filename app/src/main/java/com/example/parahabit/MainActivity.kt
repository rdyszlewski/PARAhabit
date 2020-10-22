package com.example.parahabit

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import androidx.room.Room
import com.example.parahabit.data.Habit
import com.example.parahabit.database.AppDatabase
import com.example.parahabit.database.Tables
import com.example.parahabit.database.DatabaseHandler
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread {
            val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "PARAbits").build()
//            val habit: Habit = Habit()
//            habit.name = "Pierwszy nawyk"
//            habit.description = "To jest jakiś tam nawyk"
//            db.getHabitDAO().insert(habit)

            val habits: List<Habit> = db.getHabitDAO().getAll()
            println(habits)
            Log.v("siema", habits.size.toString())
        }
    }


}