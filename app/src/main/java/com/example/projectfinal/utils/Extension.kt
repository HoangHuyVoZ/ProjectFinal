@file:Suppress("DEPRECATION")

package com.example.projectfinal.utils

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.projectfinal.R
import com.example.projectfinal.ui.main.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import java.util.regex.Pattern
fun Context.getSharedPreference(prefsName: String, key: String): String {
    getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        ?.getString(key, "Value is empty!")?.let { return it }
    return "Preference doesn't exist."
}
fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isPassValid(password: String): Boolean {
    return Patterns.PASSWORD.matcher(password).matches()
}

fun isUerValid(username: String): Boolean {
    return Patterns.USER_NAME.matcher(username).matches()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun firstTime(context: Context, first: Boolean){
    val pref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putBoolean(FIRST_TIME, first)
    editor.apply()
}


object Patterns {
    val PASSWORD: Pattern = Pattern.compile("^[a-zA-Z0-9_-]{8,30}\$")

    val USER_NAME: Pattern = Pattern.compile("^[a-z0-9_-]{1,30}\$")
    val EMAIL_ADDRESS: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
}
fun HomeActivity.hideBottomNav(checkBottom : Boolean){
    if(checkBottom)
    this.bottom.visibility = View.GONE
    else{
        this.bottom.visibility = View.VISIBLE
    }
}
