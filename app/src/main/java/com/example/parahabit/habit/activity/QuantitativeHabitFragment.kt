package com.example.parahabit.habit.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.models.Unit
import com.example.parahabit.habit.OptionsView
import com.google.android.material.textfield.TextInputEditText


class QuantitativeHabitFragment : Fragment(), IHabitFragment {

    private lateinit var goal: TextInputEditText
    private lateinit var unitOptions: OptionsView<Unit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_quantitative_habit, container, false)
        initView(view)
        val habit = arguments?.getParcelable<Habit>("habit")
        setView(habit!!)
        return view
    }

    private fun initView(view:View){
        goal = view.findViewById(R.id.goal)

        val units = arrayOf(Unit.NONE, Unit.LITRE, Unit.KM)
        unitOptions = OptionsView.Builder<Unit>().setView(view.findViewById(R.id.unit))
            .setTitle("Wybierz jednostkę").setValues(units).build()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuantitativeHabitFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun setupHabit(habit: Habit) {
        habit.goal = goal.text.toString().toInt()
        habit.unit = unitOptions.getValue()
    }

     fun setView(habit: Habit?) {
         if(habit != null){
             goal.setText(habit!!.goal.toString())
             unitOptions.setActualValue(habit!!.unit)

         }
    }
}