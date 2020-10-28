package com.example.parahabit.habit.activity

import androidx.fragment.app.Fragment
import com.example.parahabit.data.models.HabitType

class HabitFragmentFactory {

    private val fragments = HashMap<HabitType, Fragment>()

    fun getFragment(habitType: HabitType): Fragment{
        if(fragments.containsKey(habitType)){
            return fragments[habitType]!!
        }
        val fragment = createFragment(habitType)
        fragments[habitType] = fragment
        return fragment
    }

    private fun createFragment(habitType: HabitType):Fragment{
        return when(habitType){
            HabitType.NORMAL->StandardHabitFragment()
            HabitType.TIME->TimeHabitFragment()
            HabitType.REPETITIONS->RepetitionsHabitFragment()
            HabitType.QUANTITATIVE->QuantitativeHabitFragment()
        }
    }
}