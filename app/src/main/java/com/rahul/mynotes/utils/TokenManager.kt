package com.rahul.mynotes.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor (@ApplicationContext context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(ApplicationConstant.PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun setToken(token:String){
        var editor = prefs.edit()
        editor.putString(ApplicationConstant.PREFS_USER_TOKEN, token)
        editor.apply()
    }

    fun getToken(): String?{
        return prefs.getString(ApplicationConstant.PREFS_USER_TOKEN, null)
    }
}