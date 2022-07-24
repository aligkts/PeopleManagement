package com.aligkts.peoplemanagement.internal.util.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

/*
 * Shows a toast message with the given string resource
 */
fun Context.toast(@StringRes messageResId: Int, length: Int = Toast.LENGTH_SHORT) {
    toast(getString(messageResId), length)
}