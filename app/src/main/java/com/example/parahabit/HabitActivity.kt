package com.example.parahabit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.models.HabitType
import com.example.parahabit.data.models.HabitTypeConverter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class HabitActivity : AppCompatActivity() {

    private lateinit var habit: Habit;
    private lateinit var typesView: TextInputEditText;
    private var checkedType = 0
    private lateinit var types: Array<String?>;

    override fun onCreate(savedInstanceState: Bundle?) {
        habit = Habit() // TODO: później można gdzieś to przenieść
        types =  createTypes()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit)
        // TODO: pobrać przesłany obiekt
        initTypes()
    }

    private fun initTypes(){
        typesView = findViewById(R.id.type)
        typesView.setOnFocusChangeListener{view, focus ->
            if(focus){
                showTypeDialog()
            }
        }
        val typeText = types[checkedType]
        typesView.setText(typeText)
        // TODO: zrobić zaznaczenie odpowiednieogo typu

    }

    private fun showTypeDialog(){
        // TODO: być może można napisać jakiś własny widget, który ułatwi pracę z tym
        // TODO: trzeba pobrać te wartości ze stringa
        // TODO: zrobić zarządzanie wybranymi elementami

        // TODO: to jest istotne
        MaterialAlertDialogBuilder(this)
            .setTitle("Wybierz typ")
            .setNeutralButton("Anuluj"){dialog, which->
            }
                // TODO: prawdopodobnie będzie trzeba napisać adapter
            .setSingleChoiceItems(types, checkedType){ dialog, which ->
                println(which)
                val type = HabitTypeConverter.toHabitType(which)
                println(type)
                habit.type = type
                typesView.setText(HabitTypeConverter.getStringResource(type))
                checkedType = which
                dialog.dismiss()
            }
            .setOnDismissListener{
                typesView.clearFocus()
            }
            .show()
    }

    private fun createTypes(): Array<String?> {
        val values = HabitType.values()
        val types = arrayOfNulls<String>(values.size)
        for (i in 0..values.size-1) {
            val type = values[i]
            val resource = HabitTypeConverter.getStringResource(type)
            val text = getString(resource)
            types[i] = text
        }
        return types
    }

}