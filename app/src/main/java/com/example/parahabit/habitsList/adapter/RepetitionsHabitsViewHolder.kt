package com.example.parahabit.habitsList.adapter

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit
import com.google.android.material.button.MaterialButton

class RepetitionsHabitsViewHolder(view: View, context: Activity) : HabitsViewHolder(view, context) {

    private val nameText = view.findViewById<TextView>(R.id.name)
    private val amountText = view.findViewById<TextView>(R.id.amount)
    private val addButton = view.findViewById<MaterialButton>(R.id.add_button)
    private val menuButton = view.findViewById<MaterialButton>(R.id.menu_button)
    private val doneText = view.findViewById<TextView>(R.id.done_text)
    private val progressBar = view.findViewById<ProgressBar>(R.id.progress)

    private val doneHelper = DoneViewHelper.Builder(view).setDoneText(doneText)
            .setInvisibleViews(listOf(addButton)).build()

    override fun bind(habit: Habit) {
        this.habit = habit
        nameText.text = habit.name
        addButton.setOnClickListener {
            addExecution(habit, 1)
        }
        progressBar.max = habit.goal
        updateView(habit)
    }

    private fun updateAmountText(habit:Habit){
        val amount = habit.executions.size
        val text = "${amount}/${habit.goal}"
        amountText.text = text
    }


    override fun setDone(done:Boolean){
        doneHelper.setDone(done)
    }

    override fun updateViewHolder(habit:Habit){
        updateAmountText(habit)
        progressBar.progress = habit.executions.size
    }


}