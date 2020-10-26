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
    var id: Long = -1

    var habit: Long? = null

    @TypeConverters(DateConverter::class)
    var date: Date? = null

    // TODO: prawdopodobnie powinien być tutaj jakiś inny typ. MOżna zrobić to w ten sposób, że data przechowywana jest w jednej wartości, a zapisywana w dwóch
    @TypeConverters(TimeConverter::class)
    var time:Date? = null

    var amount: Int = -1

}