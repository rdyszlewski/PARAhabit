package com.example.parahabit.habitsList.adapter

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit
import com.google.android.material.button.MaterialButton

class QuantitativeHabitsViewHolder(val view: View) : HabitsViewHolder(view) {

    private val nameText = view.findViewById<TextView>(R.id.name)
    private val amountText = view.findViewById<TextView>(R.id.amount)
    private val doneText = view.findViewById<TextView>(R.id.done_text)
    private val addButton = view.findViewById<MaterialButton>(R.id.add_button)
    private val menuButton = view.findViewById<MaterialButton>(R.id.menu_button)
    private val progressBar = view.findViewById<ProgressBar>(R.id.progress)

    override fun bind(habit: Habit) {
        this.habit = habit
        nameText.text = habit.name
        addButton.setOnClickListener {
            //
        }
        updateView(habit)
    }

    private fun showNumberDialog(){
        // TODO: pokazanie dialogu
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