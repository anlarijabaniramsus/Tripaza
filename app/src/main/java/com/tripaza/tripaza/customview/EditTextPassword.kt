package com.tripaza.tripaza.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputLayout
import com.tripaza.tripaza.helper.Validator

class EditTextPassword: AppCompatEditText, View.OnTouchListener{
    constructor(context: Context): super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr){
        init()
    }

    private fun init(){
        addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                errorHandler()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        errorHandler()
        return false
    }

    private fun errorHandler() {
        val textInputLayout = this.parent.parent as TextInputLayout

        val validity = Validator.isPasswordValid(context, text.toString())
        if (validity.valid){
            textInputLayout.error = null
        }else{
            textInputLayout.error = validity.errorMessage
        }
    }
}