package com.example.parahabit.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.parahabit.data.models.converters.HabitTypeConverter
import com.example.parahabit.data.models.converters.PeriodConverter
import com.example.parahabit.data.models.converters.UnitConverter
import java.io.Serializable

@Entity(tableName="habits")
class Habit() : Parcelable{
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

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        name = parcel.readString().toString() // TODO: sprawdzić, czy to ma sens
        description = parcel.readString().toString() // TODO: sprawdzić, czy to ma sens
        goal = parcel.readInt()
        currentAmount = parcel.readInt()

        // TODO: nie ma enumów. Czy to dobrze?
    }

    fun isFinished():Boolean{
        return currentAmount >= goal
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(goal)
        parcel.writeInt(currentAmount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Habit> {
        override fun createFromParcel(parcel: Parcel): Habit {
            return Habit(parcel)
        }

        override fun newArray(size: Int): Array<Habit?> {
            return arrayOfNulls(size)
        }
    }

}