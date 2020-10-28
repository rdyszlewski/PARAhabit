package com.example.parahabit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
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
        // TODO: to raczej powinno być w fragmencie
        val typesView = findViewById<TextInputEditText>(R.id.type)
        typeOptions = OptionsView(typesView, HabitTypeResource())
        // TODO: dlaczego nie wstawić tego od razu do konstruktora?
        typeOptions.init(habit, "Wybierz typ", {type->changeType(type)})
    }

    private fun changeType(type:HabitType){
        habit.type = type
        changeFragment(getFragment(type))

    }

    private fun getFragment(type:HabitType):Fragment{
        // TODO: zaktualizować to
        return when(type){
            HabitType.NORMAL->RepetitionsHabitFragment()
            HabitType.TIME-> TimeHabitFragment()
            HabitType.REPETITIONS->RepetitionsHabitFragment()
            HabitType.QUANTITATIVE->TimeHabitFragment()
        }
    }

    private fun changeFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}