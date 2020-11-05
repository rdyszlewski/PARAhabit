package com.example.parahabit.habit.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.parahabit.R
import com.example.parahabit.commands.SaveHabitCommand
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.models.HabitType
import com.example.parahabit.data.repository.Repository
import com.example.parahabit.habit.OptionsView
import com.google.android.material.button.MaterialButton

class HabitActivity : AppCompatActivity() {

    private lateinit var habit: Habit;
    private lateinit var typeOptions: OptionsView<HabitType>
    private lateinit var mainFragment: HabitFragment
    private lateinit var secondFragment: IHabitFragment;
    private lateinit var saveButton: MaterialButton

    private val fragmentFactory = HabitFragmentFactory()

    override fun onCreate(savedInstanceState: Bundle?) {

        val currentHabit = intent.getParcelableExtra<Habit>("habit")
        if(currentHabit==null){
            habit = Habit()
        } else {
            habit = currentHabit
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit)
        initView()
        initFragments()
    }

    private fun initView(){
        saveButton = findViewById(R.id.saveButton)
        saveButton.setOnClickListener{
            saveHabit()
        }
    }

    private fun saveHabit(){
        val habit = mainFragment.getHabit()
        secondFragment.setupHabit(habit)

        val command = SaveHabitCommand(habit, Repository.getInstance())
        command.setCallback { savedHabit->closeView(savedHabit) }
        command.execute()
    }

    private fun closeView(habit: Habit){
        println("Zamykam widok")
        // TODO: wysłać jeszcze informacje, który element został zaktualizowany
        val resultIntent = Intent()
        resultIntent.putExtra("habit", habit)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun initFragments(){
        val bundle = Bundle()
        bundle.putParcelable("habit", habit)
        mainFragment = supportFragmentManager.findFragmentById(R.id.habit_fragment) as HabitFragment
        mainFragment.init(habit) { type -> changeType(type) }

        changeType(habit.type)
    }

    private fun changeType(type:HabitType){
        val fragment = fragmentFactory.getFragment(type)
        val bundle = Bundle()
        bundle.putParcelable("habit", habit)
        fragment.arguments = bundle
        changeFragment(fragment)
        secondFragment = fragment as IHabitFragment

        // TODO: to będzie trzeba zrobić jakoś inaczej
    }

    private fun changeFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        finish()
    }
}