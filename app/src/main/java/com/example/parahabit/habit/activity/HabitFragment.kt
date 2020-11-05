package com.example.parahabit.habit.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.models.HabitType
import com.example.parahabit.data.models.Period
import com.example.parahabit.data.resources.HabitTypeResource
import com.example.parahabit.habit.OptionsView
import com.google.android.material.textfield.TextInputEditText
import org.jetbrains.annotations.NotNull
class HabitFragment : Fragment() {
    // TODO: zrobić refaktoryzację tego fragmentu

    private lateinit var name: TextInputEditText
    private lateinit var description: TextInputEditText
    private lateinit var typeOptions: OptionsView<HabitType>
    private lateinit var periodOptions: OptionsView<Period>
    private lateinit var fragmentView: View

    private var habit: Habit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private fun initView(view: View){
        name = view.findViewById(R.id.name)
        description = view.findViewById(R.id.description)
    }

    fun init(habit: Habit,callback: (type:HabitType)->Unit){
        this.habit = habit
        typeOptions = OptionsView.Builder<HabitType>()
                .setView(fragmentView.findViewById(R.id.type))
                .setTitle("Wybierz typ").setValues(HabitType.values())
                .setValue(habit.type).setCallback(callback).build()

        periodOptions = OptionsView.Builder<Period>()
                .setView(fragmentView.findViewById(R.id.period))
                .setTitle("Wybierz okres").setValues(Period.values())
                .setValue(habit.period).build()

        name.setText(habit.name)
        description.setText(habit.description)
    }


    fun getHabit():Habit{
        // TODO: utworzenie
        if(habit == null){
            habit = Habit()
        }
        habit!!.name = name.text.toString()
        habit!!.description = description.text.toString()
        habit!!.type = typeOptions.getValue()
        return habit as Habit
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_habit, container, false)
        this.fragmentView = view
        initView(view)
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HabitFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}

