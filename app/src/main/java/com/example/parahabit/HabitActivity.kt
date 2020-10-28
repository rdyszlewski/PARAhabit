package com.example.parahabit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.models.HabitType
import com.example.parahabit.data.resources.HabitTypeResource
import com.example.parahabit.habits.OptionsView
import com.google.android.material.textfield.TextInputEditText

class HabitActivity : AppCompatActivity() {

    private lateinit var habit: Habit;
    private lateinit var typeOptions: OptionsView<HabitType>

    override fun onCreate(savedInstanceState: Bundle?) {
        habit = Habit()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit)
        initTypes()
    }

    private fun initTypes(){
        val typesView = findViewById<TextInputEditText>(R.id.type)
        typeOptions = OptionsView(typesView, HabitTypeResource())
        // TODO: dlaczego nie wstawić tego od razu do konstruktora?
        typeOptions.init(habit, "Wybierz typ", {type->habit.type = type})
    }
}