package com.example.parahabit.habit

import android.content.Context
import android.text.InputFilter
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.example.parahabit.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

// TODO: refakotryzacja
class NumberDialog(private val context: Context, private val viewResource: Int, private val itemResource: Int) {

    private var dialog : AlertDialog? = null
    private val inputView: View = LayoutInflater.from(context).inflate(viewResource, null, false)
    private var input: TextInputEditText
    private var callback: ((Int)->Unit)? = null
    private var min: Int = 0
    private var max: Int = 0
    private var title: String? = null

    fun setRange(min: Int, max:Int){
        this.min = min
        this.max = max

        input.filters = arrayOf(MinMaxFilter(min, max))
    }

    fun setCallback(callback: (Int)->Unit) {
        this.callback = callback
    }

    init {
        input = inputView.findViewById(R.id.number_input)
    }

    fun show(){
        if(dialog == null){
            dialog = createDialog()
            input.setOnEditorActionListener { v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    accept()
                    true
                }
                false
            }
        }
        dialog!!.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        dialog!!.show()
        input.requestFocus()
    }

    private fun createDialog(): AlertDialog{
        val builder = MaterialAlertDialogBuilder(context)
                .setView(inputView)
                .setPositiveButton("OK"){dialog, which ->
                    accept()
                }
                .setOnDismissListener {
                    hideKeyboard(context)
                }
        if(title != null){
            builder.setTitle(title)
        }
        return builder.create()
    }

    private fun accept(){
        if(validate(input)){
            callback?.invoke(getValue())
        } else {
            input.setError("Nieprawidłowa wartość")
        }
    }

    private fun validate(input:TextInputEditText):Boolean{
        val text = input.text
        if(text!!.isNotEmpty()){
            return if(min != 0  && max != 0){
                val value = text.toString().toInt()
                value in min..max
            } else {
                true
            }
        }
        return false
    }

    private fun hideKeyboard(context: Context){
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    private fun getValue():Int{
        return input.text.toString().toInt()
    }

    private class MinMaxFilter(private val min:Int, private val max:Int) : InputFilter {

        override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
            println(source.toString())
            println(dest.toString())

            if(source.isNullOrEmpty()){
                return ""
            }
            val text = dest.toString() + source.toString()
            val value = text.toInt()
            if(value in min..max){
                return null
            }
            return ""
            // TODO: można zrobić takie coośm, że po skasowaniu wchodzi 1, a nastepne kliknięcie ustawia wartość
        }
    }
}