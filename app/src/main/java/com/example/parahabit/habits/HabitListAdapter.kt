package com.example.parahabit.habits

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.parahabit.R
import com.example.parahabit.data.models.Habit

class HabitListAdapter(private val context: Activity, private val habits: Array<Habit>, private val title: String)
    :ArrayAdapter<Habit>(context, R.layout.activity_main, habits){

    private val layoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: ViewHolder
        val rowView: View?
        if(convertView == null){
            rowView = layoutInflater.inflate(R.layout.habit_item, parent, false)
            viewHolder = ViewHolder(rowView)
            rowView.tag = viewHolder
        } else {
            rowView = convertView;
            viewHolder = rowView.tag as ViewHolder
        }
        val habit = habits[position]
        viewHolder.nameText.text = habit.name
        viewHolder.button.setOnClickListener{println("CLick")}
        return rowView!!
    }

    private class ViewHolder(view: View?){
        var nameText: TextView = view?.findViewById(R.id.name) as TextView
        var button: Button = view?.findViewById(R.id.done_button) as Button
    }
}