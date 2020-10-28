package com.example.parahabit.habit.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit


class StandardHabitFragment : Fragment(), IHabitFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.fragment_standard_habit, container, false)
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                StandardHabitFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    override fun setupHabit(habit: Habit) {

    }
}