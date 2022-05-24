package com.tripaza.tripaza.helper

import android.content.Context

class ValidatorResult(var valid: Boolean,var errorMessage: String)
object Validator {
    fun isEmailValid(context: Context, email: String): ValidatorResult{
        return if(email.isEmpty())
                    ValidatorResult(false, "Email can't be empty")
                else if(email[0].isWhitespace())
                    ValidatorResult(false, "password should start with character")
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    ValidatorResult(false, "Email is not valid")
                else
                    ValidatorResult(true, "Valid")
    }
    
    fun isPasswordValid(context: Context, password: String): ValidatorResult{
        return if(password.isEmpty()){
                    ValidatorResult(false, "Password can't be empty")
                }else if(password[0].isWhitespace()){
                    ValidatorResult(false, "password should start with character")
                }else if(password.length < 8){
                    ValidatorResult(false, "Password should more than 8 character")
                }else
                    ValidatorResult(true, "Valid")
    }
    
    fun isPhoneValid(context: Context, phone: String): ValidatorResult{
        return if(phone.isEmpty())
                    ValidatorResult(false, "Phone number can't be empty")
                else if(phone[0].isWhitespace())
                    ValidatorResult(false, "phone should start with character")
                else if(phone.length < 8)
                    ValidatorResult(false, "Phone should more than 8 character")
                else if(!android.util.Patterns.PHONE.matcher(phone).matches())
                    ValidatorResult(false, "Phone is not valid")
                else
                    ValidatorResult(true, "Valid")
    }
    fun isInputValid(context: Context, text: String): ValidatorResult{
        return if(text.isEmpty())
            ValidatorResult(false, "Field can't be empty")
        else if(text[0].isWhitespace())
            ValidatorResult(false, "Field should start with character")
        else
            ValidatorResult(true, "Valid")
    }
}