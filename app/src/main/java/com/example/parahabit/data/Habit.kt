package com.example.parahabit.data

class Habit{
    var id: Int = -1
    var name: String = ""
    var description: String = ""
    var type: HabitType = HabitType.NOMARL
    var period: Period = Period.DAY
    var goal: Int = 0
    var unit: Unit = Unit.NONE

}