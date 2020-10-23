package com.example.parahabit.data.models

import androidx.room.TypeConverter
import java.util.*

class DateConverter{

    companion object{

        @TypeConverter
        @JvmStatic
        fun toNumber(date: Date):Int{
            val calendar = GregorianCalendar()
            calendar.time= date
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val yearValue = year - 2000;
            val value = yearValue * 10000 + month * 100 + day
            return value
        }

        @TypeConverter
        @JvmStatic
        fun toDate(number: Int):Date{
            val year = number / 10000 + 2000
            val day = number % 100
            val month = number % 10000 / 100
            val calendar = GregorianCalendar()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            return calendar.time;
        }

    }
}