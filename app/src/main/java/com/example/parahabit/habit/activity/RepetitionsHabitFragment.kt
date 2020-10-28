package com.example.parahabit.habit.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit
import com.google.android.material.textfield.TextInputEditText

class RepetitionsHabitFragment : Fragment(), IHabitFragment {


    private lateinit var goal: TextInputEditText

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
        val view =inflater.inflate(R.layout.fragment_repetitions_habit, container, false)
        initView(view)
        return view
    }

    private fun initView(view:View){
        goal = view.findViewById(R.id.goal)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RepetitionsHabitFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun setupHabit(habit: Habit) {
        habit.goal = goal.text.toString().toInt()
    }
}