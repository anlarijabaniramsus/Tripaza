package com.tripaza.tripaza.helper

import android.content.Context
import com.tripaza.tripaza.helper.Constants.USER_DATA_MIN_PASSWORD_LENGTH
import com.tripaza.tripaza.helper.Constants.USER_DATA_MIN_PHONE_LENGTH

class ValidatorResult(var valid: Boolean,var errorMessage: String)
object Validator {
    fun isEmailValid(email: String): ValidatorResult{
        return if(email.isEmpty())
                    ValidatorResult(false, "Email can't be empty")
                else if(email[0].isWhitespace())
                    ValidatorResult(false, "password should start with character")
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    ValidatorResult(false, "Email is not valid")
                else
                    ValidatorResult(true, "Valid")
    }
    
    fun isPasswordValid(password: String): ValidatorResult{
        return if(password.isEmpty()){
                    ValidatorResult(false, "Password can't be empty")
                }else if(password[0].isWhitespace()){
                    ValidatorResult(false, "password should start with character")
                }else if(password.length < USER_DATA_MIN_PASSWORD_LENGTH){
                    ValidatorResult(false, "Password should more than $USER_DATA_MIN_PASSWORD_LENGTH character")
                }else
                    ValidatorResult(true, "Valid")
    }
    
    fun isPhoneValid(phone: String): ValidatorResult{
        return if(phone.isEmpty())
                    ValidatorResult(false, "Phone number can't be empty")
                else if(phone[0].isWhitespace())
                    ValidatorResult(false, "phone should start with character")
                else if(phone.length < USER_DATA_MIN_PHONE_LENGTH)
                    ValidatorResult(false, "Phone should more than $USER_DATA_MIN_PHONE_LENGTH character")
                else if(!android.util.Patterns.PHONE.matcher(phone).matches())
                    ValidatorResult(false, "Phone is not valid")
                else
                    ValidatorResult(true, "Valid")
    }
    
    fun isInputValid(text: String): ValidatorResult{
        return if(text.isEmpty())
            ValidatorResult(false, "Field can't be empty")
        else if(text[0].isWhitespace())
            ValidatorResult(false, "Field should start with character")
        else
            ValidatorResult(true, "Valid")
    }
}