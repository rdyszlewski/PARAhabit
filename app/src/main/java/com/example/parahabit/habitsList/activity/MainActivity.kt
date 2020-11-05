package com.example.parahabit.habitsList.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.parahabit.habit.activity.HabitActivity
import com.example.parahabit.R
import com.example.parahabit.data.database.DatabaseRepository
import com.example.parahabit.data.models.converters.DateConverter
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.repository.IRepository
import com.example.parahabit.data.repository.Repository
import com.example.parahabit.habitsList.adapter.HabitsAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

// TODO: zrobić klasę zarzadzającą obecną datą
// TODO: refaktoryzacja

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: HabitsAdapter
    private lateinit var repository: IRepository

    private lateinit var addButton: FloatingActionButton
    private lateinit var filterButton: MaterialButton
    private var filtered: Boolean = false;

    private lateinit var dayText: TextView
    private lateinit var decreaseDay: MaterialButton
    private lateinit var increaseDay: MaterialButton
    private var currentDate: Calendar = Calendar.getInstance() // TODO: chyba jakoś tak powinno być

    override fun onCreate(savedInstanceState: Bundle?) {
        println("Tworzenie aktywności")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initRepository()
        updateDayText()
        loadHabits()

    }

    private fun initRepository() {
        Repository.getInstance().setRepository(DatabaseRepository(applicationContext))
        repository = Repository.getInstance().getRepository()
    }

    private fun initView() {
        val listView: RecyclerView = findViewById(R.id.habit_list)
        adapter = HabitsAdapter(ArrayList(), this)

        registerForContextMenu(listView)
        listView.adapter = adapter

        addButton = findViewById(R.id.addHabitButton)
        addButton.setOnClickListener{openHabitActivity(null)}

        filterButton = findViewById(R.id.filter_button)
        filterButton.setOnClickListener {
            filtered = !filtered
            adapter.filter(filtered)
        }

        dayText = findViewById(R.id.day_text)
        increaseDay = findViewById(R.id.increase_day)
        increaseDay.setOnClickListener {changeDay(1)  }
        decreaseDay = findViewById(R.id.decrease_day)
        decreaseDay.setOnClickListener { changeDay(-1) }
    }

    private fun changeDay(number: Int){
        currentDate.add(Calendar.DAY_OF_MONTH, number)
        updateDayText()
        loadHabits()
    }

    private fun updateDayText() {
        if (equalDate(currentDate, Calendar.getInstance())) {
            dayText.text = "Dzisiaj"
        } else {
            dayText.text = currentDate.time.toString()
        }
    }

    private fun equalDate(date1: Calendar, date2: Calendar): Boolean{
        return (date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH)
                && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)
                && date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR))
    }

    private fun openHabitActivity(habit: Habit?){
        val intent = Intent(applicationContext, HabitActivity::class.java)
        intent.putExtra("habit", habit)
        startActivityForResult(intent, 100) // TODO: dodać tutaj jakąś stałą
    }

    private fun loadHabits() {
        thread {
            val habits: List<Habit> = repository.getHabitRepository().getAll()
            val dateValue = DateConverter.toNumber(currentDate.time)
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
                    if(habit!= null){
                        adapter.addHabit(habit)
                    }
                    // TODO: można też zrobić w ten sposób, że przekazuje id, i pobiera nawyk o określonym id
                    println(habit?.name)
                }
            }
            200 ->{
                if(resultCode == Activity.RESULT_OK){
                    val habit = data?.getParcelableExtra<Habit>("habit")
                    if(habit != null){
                        adapter.replaceHabit(habit)
                    }
                    println("Udało się to wszystko jak fajnei ojej")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Niszczenie aktywności")

    }

    override fun onStop() {
        super.onStop()
        println("Schowanie aktywności")
    }

    override fun onRestart() {
        super.onRestart()
        adapter.updateView()
        println("Wznawianie aktywności")
    }
}