package com.example.parahabit.data.models

import androidx.annotation.NonNull
import androidx.room.*
import java.util.*

@Entity(tableName="executions",
        foreignKeys = arrayOf(ForeignKey(entity = Habit::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("habit"),
                onDelete = ForeignKey.CASCADE)))
class HabitExecution{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = -1
    // TODO: sprawdzić, jak powinno się to robić. Może wystarczy samo id
    var habit: Int? = null

    @TypeConverters(DateConverter::class)
    var date: Date? = null

    // TODO: prawdopodobnie powinien być tutaj jakiś inny typ
    @TypeConverters(TimeConverter::class)
    var time:Date? = null

    var amount: Int = -1

}