package com.example.parahabit.data.models

import androidx.room.TypeConverter
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
    }
}