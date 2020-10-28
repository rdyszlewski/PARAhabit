package com.example.parahabit.data.models.converters

import androidx.room.TypeConverter
import com.example.parahabit.data.models.Period
import java.lang.IllegalArgumentException

class PeriodConverter{
    companion object{
        @TypeConverter
        @JvmStatic
        fun toPeriod(periodNumber: Int): Period {
            return when(periodNumber){
                0-> Period.DAY
                1-> Period.WEEK
                2-> Period.MONTH
                3-> Period.TWO_WEEKS
                else -> throw IllegalArgumentException()
            }
        }

        @TypeConverter
        @JvmStatic
        fun toNumber(period: Period): Int{
            return when(period){
                Period.DAY->0
                Period.WEEK->1
                Period.MONTH->2
                Period.TWO_WEEKS->3
            }
        }
    }
}