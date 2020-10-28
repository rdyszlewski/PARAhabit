package com.example.parahabit.data.resources

import android.content.Context
import com.example.parahabit.R
import com.example.parahabit.data.models.HabitType

class HabitTypeResource: EnumResource<HabitType>() {

    // TODO: dowiedzieć się, w jaki sposób można to zrobić inaczej
    // TODO: można to przenieść do enumów
    override fun getStringResource(type: HabitType):Int{
        return when(type){
            HabitType.NORMAL-> R.string.normal_habit
            HabitType.TIME-> R.string.time_habit
            HabitType.QUANTITATIVE-> R.string.quantitative_habit
            HabitType.REPETITIONS-> R.string.repetitions_habit
        }
    }

    override fun getStrings(context: Context): Array<String> {
        return getStrings(context, HabitType.values())
    }

    override fun getValue(ordinal: Int): HabitType {
        return HabitType.values()[ordinal]
    }


}

abstract  class EnumResource<T: Enum<T>>{

    abstract fun getStringResource(type: T):Int
    abstract fun getStrings(context: Context): Array<String>
    abstract fun getValue(ordinal: Int): T

    protected fun getStrings(context: Context, enums: Array<T>): Array<String>{
        val result = Array(enums.size){""}
        for(i in 0..enums.size-1){
            val value = enums[i]
            val resource = getStringResource(value)
            val text = context.getString(resource)
            result[i] = text
        }
        return result
    }
}