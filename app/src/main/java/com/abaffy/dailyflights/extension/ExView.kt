package com.abaffy.dailyflights.extension

import android.view.View

inline fun <T : View> T.visible(condition: () -> Boolean): T {
    this.visibility = if (condition.invoke()) View.VISIBLE else View.GONE
    return this
}

fun <T : View> T.visible(): T {
    this.visibility = View.VISIBLE
    return this
}

inline fun <T : View> T.invisible(condition: () -> Boolean): T {
    this.visibility = if (condition.invoke()) View.INVISIBLE else View.VISIBLE
    return this
}

fun <T : View> T.invisible(): T {
    this.visibility = View.INVISIBLE
    return this
}

inline fun <T : View> T.gone(condition: () -> Boolean): T {
    this.visibility = if (condition.invoke()) View.GONE else View.VISIBLE
    return this
}

fun <T : View> T.gone(): T {
    this.visibility = View.GONE
    return this
}
