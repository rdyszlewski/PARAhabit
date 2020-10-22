package com.example.parahabit.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName="habits")
class Habit{
    @PrimaryKey
    @NonNull var id: Int = -1
    var name: String = ""
    var description: String = ""
    @TypeConverters(HabitTypeConverter::class)
    var type: HabitType = HabitType.NORMAL
    @TypeConverters(PeriodConverter::class)
    var period: Period = Period.DAY
    var goal: Int = 0
    @TypeConverters(UnitConverter::class)
    var unit: Unit = Unit.NONE

}