package com.example.parahabit.data.models.converters

import androidx.room.TypeConverter
import java.util.*

class TimeConverter{

    companion object{

        @TypeConverter
        @JvmStatic
        fun toNumber(date: Date):Short{
            val calendar = GregorianCalendar()
            calendar.time = date
            val hours = calendar.get(Calendar.HOUR)
            val minutes = calendar.get(Calendar.MINUTE)
            val value = hours * 1000 + minutes
            return value.toShort()
        }

        @TypeConverter
        @JvmStatic
        fun toTime(number: Short):Date{
            val hours = number / 1000
            val minutes = number % 100
            val calendar = GregorianCalendar()
            calendar.set(Calendar.HOUR, hours)
            calendar.set(Calendar.MINUTE, minutes)
            return calendar.time
        }
    }
}