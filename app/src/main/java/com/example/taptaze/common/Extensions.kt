package com.example.taptaze.common

import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ContentInfoCompat.Flags
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context).load(url).into(this)
}

fun TextView.strikeText(){
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.GONE
}

fun String.isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    if (!email.isNullOrEmpty() && email.matches(emailRegex.toRegex())) {
        return true
    }
    return false
}

fun String.isValidPassword(password:String): Boolean{
    if(!password.isNullOrEmpty() && password.length >= 6){
        return true
    }
    return false
}
