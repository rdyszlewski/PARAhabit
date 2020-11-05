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
import kotlinx.android.synthetic.main.fragment_time_habit.*


class TimeHabitFragment : Fragment(), IHabitFragment {

    private lateinit var goal: TextInputEditText
    private lateinit var unitOptions: OptionsView<Unit>

    private var habit: Habit? = null
    // TODO: zrobić to dla jednostek. Będzie trzeba ograniczyć jakoś jednostki

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            // TODO: tutaj można pobrać jakieś parametry
        }
    }

    private fun initView(view:View){
        goal = view.findViewById(R.id.goal)

        val timeValues = arrayOf(Unit.MINUTE, Unit.HOUR)
        // TODO: wstawić wartość. Będzie trzbea tutaj jakoś przekazać wartość
        unitOptions = OptionsView.Builder<Unit>().setView(view.findViewById(R.id.unit))
                .setTitle("Wybierz jednostkę").setValues(timeValues).build()

        // TODO: to powinno być zrobione jakoś inaczej
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_time_habit, container, false)
        initView(view)
        val habit = arguments?.getParcelable<Habit>("habit")
        setView(habit)
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TimeHabitFragment().apply {
                arguments = Bundle().apply {
                    // TODO: sprawdzić jak to wszystko będzie działać
                }
            }
    }

    override fun setupHabit(habit: Habit) {
        habit.goal = goal.text.toString().toInt()
        habit.unit = unitOptions.getValue()
    }

    // TODO:  to raczej nie powinno być w interfejsie
     fun setView(habit: Habit?) {
//        this.habit = habit
        if(habit != null){
            goal.setText(habit.goal.toString())
            unitOptions.setActualValue(habit.unit)

        }
    }
}