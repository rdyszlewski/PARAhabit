package com.example.parahabit.habitsList.adapter

import android.app.Activity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit
import com.google.android.material.button.MaterialButton


class QuantitativeHabitsViewHolder(view: View, context: Activity) : HabitsViewHolder(view, context) {

    private val nameText = view.findViewById<TextView>(R.id.name)
    private val amountText = view.findViewById<TextView>(R.id.amount)
    private val doneText = view.findViewById<TextView>(R.id.done_text)
    private lateinit var addButton: MaterialButton
    private val menuButton = view.findViewById<MaterialButton>(R.id.menu_button)
    private val progressBar = view.findViewById<ProgressBar>(R.id.progress)

    private val doneHelper = DoneViewHelper.Builder(view).setInvisibleViews(listOf(addButton))
            .setDoneText(doneText).build()

    override fun bind(habit: Habit) {
        this.habit = habit
        nameText.text = habit.name
        addButton = NumberButtonHelper.init(R.id.add_button, context){ value->addExecution(habit, value)} as MaterialButton
        progressBar.max = habit.goal
        updateView(habit)
    }

    override fun setDone(done:Boolean){
        doneHelper.setDone(done)
    }

    override fun updateViewHolder(habit:Habit){
        AmountTextFormatter.updateText(habit, amountText)
        progressBar.progress = habit.getExecutionsValue()
    }
}