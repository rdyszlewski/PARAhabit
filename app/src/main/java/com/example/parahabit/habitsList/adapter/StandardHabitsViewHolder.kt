package com.example.parahabit.habitsList.adapter

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.example.parahabit.R
import com.example.parahabit.commands.AddExecutionCommand
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.repository.Repository
import com.google.android.material.button.MaterialButton

class StandardHabitsViewHolder(view: View, context: Activity) : HabitsViewHolder(view, context) {

    private val name = view.findViewById<TextView>(R.id.name)
    private val doneText = view.findViewById<TextView>(R.id.done_text)
    private val doneButton = view.findViewById<MaterialButton>(R.id.done_button)
    private val menuButton = view.findViewById<MaterialButton>(R.id.menu_button)

    private val doneHelper = DoneViewHelper.Builder(view).setDoneText(doneText)
            .setGoneViews(listOf(doneButton)).build()

    override fun bind(habit: Habit) {
        this.habit = habit
        setDone(habit.isFinished())
        name.text = habit.name
        doneButton.setOnClickListener {
            addExecution(habit, 1)
        }
    }

    override fun updateViewHolder(habit:Habit){

    }

    override fun setDone(done:Boolean){
       doneHelper.setDone(done)
    }

}