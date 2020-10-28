package com.example.parahabit.habits

import android.content.Context
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.resources.EnumResource
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

// TODO: może to przenieść do innego pakietu
class OptionsView<T : Enum<T>>(private val view: TextInputEditText,val resourceConverter: EnumResource<T>){
    private var checkedType = 0
    private var actualValue: T? = null

    // TODO; z tą wartością można jeszcze coś pokombinować
    fun init(habit: Habit, title: String, callback:(value:T)->Unit){
        val elements = getStringElements(resourceConverter, view.context)
        val value = elements[habit.type.ordinal]
        view.setText(value)

        view.setOnFocusChangeListener{view, focus ->
            if(focus){
                showDialog(title, view.context, callback)
            }
        }
    }

    fun showDialog(title: String,context: Context, callback:(value:T)->Unit) {
        val elements = getStringElements(resourceConverter, context)
        MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setNeutralButton(R.string.cancel) { dialog, which -> dialog.dismiss() }
                .setSingleChoiceItems(elements, checkedType) { dialog, which ->
                    val value = resourceConverter.getValue(which)
                    actualValue = value
                    callback(value)
                    view.setText(elements[which])
                    checkedType = which
                    dialog.dismiss()
                }
                .setOnDismissListener {
                    view.clearFocus()
                }.show()

    }

    private fun getStringElements(resourceConverter: EnumResource<T>, context: Context): Array<String>{
        val values = resourceConverter.getStrings(context)
        return values
    }
}