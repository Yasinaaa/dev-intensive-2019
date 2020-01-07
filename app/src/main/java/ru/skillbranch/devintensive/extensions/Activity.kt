package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlin.math.roundToLong

/*
 * Created by yasina on 2020-01-04
*/
fun Activity.hideKeyboard() {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}

fun Activity.convertDpToPx(dp: Float): Long {
    val r = this.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, r.displayMetrics).roundToLong()
}

fun Activity.isKeyboardOpen(): Boolean {
    val r = Rect()
    val rootView = findViewById<View>(android.R.id.content)
    rootView.getWindowVisibleDisplayFrame(r)
    val heightDiff = rootView.height - r.height()
    val marginOfError = this.convertDpToPx(50F)

    return heightDiff > marginOfError
}

fun Activity.isKeyboardClosed(): Boolean {
    return this.isKeyboardOpen().not()
}