package com.aligkts.peoplemanagement.internal.util.extension

import android.view.View

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.showIf(condition: Boolean) {
    if (condition) {
        show()
    } else {
        gone()
    }
}

