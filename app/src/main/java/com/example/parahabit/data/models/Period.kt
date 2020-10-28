package com.example.parahabit.data.models

import com.example.parahabit.R

enum class Period(val number: Int): ResourceEnum{
    DAY(0) {
        override fun getResourceId(): Int {
            return R.string.day_period
        }
    },
    WEEK(1) {
        override fun getResourceId(): Int {
            return R.string.week_period
        }
    },
    MONTH(2) {
        override fun getResourceId(): Int {
            return R.string.month_period
        }
    },
    TWO_WEEKS(3) {
        override fun getResourceId(): Int {
            return R.string.two_week_period
        }
    },
    CHOSEN(4) {
        override fun getResourceId(): Int {
            return R.string.chosen_period
        }
    }
}