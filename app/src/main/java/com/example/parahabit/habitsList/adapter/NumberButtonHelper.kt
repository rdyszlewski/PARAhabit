package com.example.parahabit.habitsList.adapter

import android.app.Activity
import android.widget.Button
import com.example.parahabit.R
import com.example.parahabit.habit.NumberDialog
import com.google.android.material.button.MaterialButton

class NumberButtonHelper {

    companion object{
        fun init(resource: Int, context: Activity, callback: (Int)->Unit): Button {
            val addButton = context.findViewById<MaterialButton>(resource)
            addButton.setOnClickListener {

                val dialog = NumberDialog(context, R.layout.number_input_layout, R.id.number_input)
                dialog.setRange(1, 500)
                dialog.setCallback(callback) // TODO: może jest możliwość zrobienia tego w inny sposób
                dialog.show()
            }
            return addButton
        }
    }


}