package com.example.parahabit.data.models

import androidx.room.TypeConverter
import com.example.parahabit.data.models.Unit
import java.lang.IllegalArgumentException

class UnitConverter{

    companion object{
        @TypeConverter
        @JvmStatic
        fun toUnit(unitNumber: Int): Unit {
            return when(unitNumber){
                0-> Unit.NONE
                1-> Unit.HOUR
                2-> Unit.MINUTE
                3-> Unit.LITRE
                else -> throw IllegalArgumentException()
            }
        }

        @TypeConverter
        @JvmStatic
        fun toNumber(unit: Unit): Int{
            return when(unit){
                Unit.NONE->0
                Unit.HOUR->1
                Unit.MINUTE->2
                Unit.LITRE->3
            }
        }
    }
}