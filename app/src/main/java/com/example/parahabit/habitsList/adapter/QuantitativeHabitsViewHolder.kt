package com.example.parahabit.habitsList.adapter

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.parahabit.R
import com.example.parahabit.commands.AddExecutionCommand
import com.example.parahabit.data.models.Habit
import com.example.parahabit.data.repository.Repository
import com.example.parahabit.habit.NumberDialog
import com.google.android.material.button.MaterialButton


class QuantitativeHabitsViewHolder(view: View, context: Activity) : HabitsViewHolder(view, context) {

    private val nameText = view.findViewById<TextView>(R.id.name)
    private val amountText = view.findViewById<TextView>(R.id.amount)
    private val doneText = view.findViewById<TextView>(R.id.done_text)
    private val addButton = view.findViewById<MaterialButton>(R.id.add_button)
    private val menuButton = view.findViewById<MaterialButton>(R.id.menu_button)
    private val progressBar = view.findViewById<ProgressBar>(R.id.progress)

    private var dialog: NumberDialog? = null

    override fun bind(habit: Habit) {
        this.habit = habit
        nameText.text = habit.name
        addButton.setOnClickListener {
            showDialog(habit)
        }
        progressBar.max = habit.goal
        updateView(habit)
        dialog = NumberDialog(view.context, R.layout.number_input_layout, R.id.number_input)
        dialog!!.setRange(1, 500)
        dialog!!.setCallback { value -> addExecution(habit, value) }
    }

    private fun showDialog(habit: Habit) {
        dialog?.show()

    }

    private fun addExecution(habit: Habit, amount: Int){
        val command = AddExecutionCommand(habit, amount, Repository.getInstance())
        command.setCallback { updatedHabit->updateView(updatedHabit) }
        command.execute()
    }

    private fun setDone(done:Boolean){
        if(done){
            doneText.visibility = View.VISIBLE
            addButton.visibility = View.INVISIBLE
            view.setBackgroundColor(Color.GREEN)
        } else {
            doneText.visibility = View.GONE
            addButton.visibility = View.VISIBLE
            view.setBackgroundColor(Color.WHITE)
        }
    }

    private fun updateAmountText(habit:Habit){
        val amount = habit.getExecutionsValue()
        val text = "${amount}/${habit.goal}"
        amountText.text = text
    }

    private fun updateView(habit:Habit){
        updateAmountText(habit)
        progressBar.progress = habit.getExecutionsValue()
        setDone(habit.isFinished())
    }
}