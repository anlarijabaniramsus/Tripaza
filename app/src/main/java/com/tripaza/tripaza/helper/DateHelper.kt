package com.tripaza.tripaza.helper

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun formatDate(day:Int, month: Int, year: Int, formatStr: String = "dd-MMM-yyyy"): String{
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, day)
        
        val formatter = SimpleDateFormat("dd-MMM-yyyy")
        return try {
            formatter.format(cal.time)
        }catch (e: Exception){
            ""
        }
    }
    fun getFormattedCurrentDate(pattern: String = "Mdd-MM-yyyy HH:mm:ss.SSS"): String{
        val c = Calendar.getInstance().time
        val sdf = SimpleDateFormat(pattern)
        return sdf.format(c)
    }
}