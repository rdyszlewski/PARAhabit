package com.example.parahabit.habitsList.adapter

import android.graphics.Color
import android.view.View
import android.widget.TextView

class DoneViewHelper {

    private var visibleViews: List<View> = ArrayList()
    private var invisibleViews: List<View> = ArrayList()
    private var goneViews: List<View> = ArrayList()
    private var doneText: TextView? = null
    private lateinit var view: View

    fun setDone(done:Boolean){
        if(done){
            visibleViews.forEach { it.visibility = View.VISIBLE }
            invisibleViews.forEach{it.visibility = View.INVISIBLE}
            goneViews.forEach { it.visibility = View.GONE }
            doneText?.visibility = View.VISIBLE
            view.setBackgroundColor(Color.GREEN)
        } else { //normal
            visibleViews.forEach{it.visibility = View.INVISIBLE}
            invisibleViews.forEach{it.visibility = View.VISIBLE}
            goneViews.forEach{it.visibility = View.VISIBLE}
            doneText?.visibility = View.GONE
            view.setBackgroundColor(Color.WHITE)
        }
    }

     class Builder(view: View){
        private val helper: DoneViewHelper = DoneViewHelper()

         init {
             helper.view = view
         }

        fun setVisibleViews(views: List<View>): Builder{
            helper.visibleViews = views
            return this
        }

        fun setInvisibleViews(views: List<View>):Builder{
            helper.invisibleViews = views
            return this
        }

        fun setGoneViews(views:List<View>):Builder{
            helper.invisibleViews = views
            return this
        }

        fun setDoneText(doneText: TextView):Builder{
            helper.doneText = doneText
            return this
        }

        fun build():DoneViewHelper{
            return helper
        }
    }
}