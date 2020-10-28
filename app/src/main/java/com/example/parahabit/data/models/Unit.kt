package com.example.parahabit.data.models

import com.example.parahabit.R

enum class Unit: ResourceEnum {
    NONE {
        override fun getResourceId(): Int {
            return R.string.none_unit
        }
    },
    HOUR {
        override fun getResourceId(): Int {
            return R.string.hour_unit
        }
    },
    MINUTE {
        override fun getResourceId(): Int {
            return R.string.minute_unit
        }
    },
    LITRE {
        override fun getResourceId(): Int {
            return R.string.litre_unit
        }
    },
    KM {
        override fun getResourceId(): Int {
            return R.string.km_unit
        }
    }
}