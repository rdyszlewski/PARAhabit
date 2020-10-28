package com.example.parahabit.habit

import android.content.Context
import android.widget.TextView
import com.example.parahabit.R
import com.example.parahabit.data.models.ResourceEnum
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// TODO: może zrobić z tego odzielny komponent
class OptionsView<T> private constructor() where T : Enum<T>, T : ResourceEnum {

    private lateinit var view: TextView
    private lateinit var values: Array<T>
    private var callback: ((value: T) -> Unit)? = null;
    private lateinit var title: String;


    private var checkedType = 0
    private var actualValue: T? = null

    private fun setActualValue(value: T) {
        actualValue = value
        runCallback(actualValue)
    }

    fun init() {
        if(actualValue == null){
            actualValue = values[0]
        }
        setText(actualValue!!)
        initClickListener()
        runCallback(actualValue)
    }

    private fun runCallback(value: T?) {
        callback?.let { it(value!!) }
    }

    private fun initClickListener() {
        view.setOnFocusChangeListener { view, focus ->
            if (focus) {
                showDialog(title, view.context, callback)
            }
        }
    }

    private fun setText(value: T) {
        val text = view.context.getString(value.getResourceId())
        view.text = text
    }

    fun getValue(): T {
        return actualValue!!
    }

    private fun showDialog(title: String, context: Context, callback: ((value: T) -> Unit)?) {
        val elements = getStringElements(context)
        MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setNeutralButton(R.string.cancel) { dialog, which -> dialog.dismiss() }
                .setSingleChoiceItems(elements, checkedType) { dialog, which ->
                    val value = setValue(which)
                    setText(value)
                    callback?.invoke(value)
                    dialog.dismiss()
                }
                .setOnDismissListener {
                    view.clearFocus()
                }.show()

    }

    private fun setValue(which: Int): T {
        val value = values[which]
        actualValue = value
        checkedType = which
        return value
    }

    private fun getStringElements(context: Context): Array<String> {
        val result = Array(values.size) { "" }
        for (i in values.indices) {
            val element = values[i]
            val text = context.getString(element.getResourceId())
            result[i] = text
        }
        return result
    }

    class Builder<T> where T : Enum<T>, T : ResourceEnum {

        private val optionsView: OptionsView<T> = OptionsView()

        fun setView(view: TextView): Builder<T> {
            optionsView.view = view
            return this;
        }

        fun setValues(values: Array<T>): Builder<T> {
            optionsView.values = values
            return this
        }

        fun setCallback(callback: (value: T) -> Unit): Builder<T> {
            optionsView.callback = callback
            return this;
        }

        fun setTitle(title: String): Builder<T> {
            optionsView.title = title
            return this;
        }

        fun setValue(value: T): Builder<T> {
            optionsView.setActualValue(value)
            return this;
        }

        fun build(): OptionsView<T> {
            optionsView.init()
            return optionsView
        }
    }
}