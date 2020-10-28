package com.example.parahabit.data.models.converters

import androidx.room.TypeConverter
import com.example.parahabit.R
import com.example.parahabit.data.models.HabitType

class HabitTypeConverter{

    companion object{
        @TypeConverter
        @JvmStatic
        fun toHabitType(habit: Int): HabitType {
            return when(habit){
                0-> HabitType.NORMAL
                1-> HabitType.TIME
                2-> HabitType.REPETITIONS
                3-> HabitType.QUANTITATIVE
                else -> throw IllegalArgumentException()
            }
        }

        @TypeConverter
        @JvmStatic
        fun toNumber(habitType: HabitType):Int{
            return when(habitType){
                HabitType.NORMAL-> 0
                HabitType.TIME-> 1
                HabitType.REPETITIONS-> 2
                HabitType.QUANTITATIVE-> 3
            }
        }

        // TODO: prawdopdobnie powinno to być w innej klasie
        fun getStringResource(habitType: HabitType): Int{
            return when(habitType){
                HabitType.NORMAL-> R.string.normal_habit
                HabitType.TIME->R.string.time_habit
                HabitType.QUANTITATIVE->R.string.quantitative_habit
                HabitType.REPETITIONS->R.string.repetitions_habit
            }
        }
    }
}