package com.example.parahabit.data.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity(tableName="habits")
class Habit : Serializable{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Long = 0
    var name: String = ""
    var description: String = ""
    @TypeConverters(HabitTypeConverter::class)
    var type: HabitType =
        HabitType.NORMAL
    @TypeConverters(PeriodConverter::class)
    var period: Period =
        Period.DAY
    var goal: Int = 0
    @TypeConverters(UnitConverter::class)
    var unit: Unit =
        Unit.NONE

    @Ignore
    var currentAmount: Int = 0

    @Ignore
    var executions: MutableList<HabitExecution> = mutableListOf()

    fun isFinished():Boolean{
        return currentAmount >= goal
    }

}