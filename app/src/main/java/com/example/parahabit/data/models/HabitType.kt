package com.example.parahabit.data.models

import com.example.parahabit.R
import java.io.Serializable

enum class HabitType: Serializable, ResourceEnum{
    NORMAL {
        override fun getResourceId(): Int {
            return R.string.normal_habit
        }
    },
    TIME {
        override fun getResourceId(): Int {
            return R.string.time_habit
        }
    },
    REPETITIONS {
        override fun getResourceId(): Int {
            return R.string.repetitions_habit
        }
    },
    QUANTITATIVE {
        override fun getResourceId(): Int {
            return R.string.quantitative_habit
        }
    }
}

interface ResourceEnum{
    fun getResourceId():Int
}