package com.rahul.mynotes.utils

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Pattern

class Helper {
    companion object{

        fun isValidEmail(email: String): Boolean{
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}