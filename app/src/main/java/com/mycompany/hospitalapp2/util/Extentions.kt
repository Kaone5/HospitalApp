package com.mycompany.hospitalapp2.util

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.hide(){
    visibility = View.GONE
}
fun View.show(){
    visibility = View.VISIBLE
}

fun View.disable(){
    isEnabled = false
}

fun View.enabled(){
    isEnabled = true
}

fun Fragment.toast(msg: String?){
    Toast.makeText(requireContext(),msg,Toast.LENGTH_LONG).show()
}
fun String.isValidEmail() =
    isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPhone() =
    isNotEmpty() && android.util.Patterns.PHONE.matcher(this).matches()
