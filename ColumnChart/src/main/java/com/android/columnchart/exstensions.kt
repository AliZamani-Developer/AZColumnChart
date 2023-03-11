package com.android.columnchart

import android.util.Log

fun Int.percentage(per: Float): Float {
    return (per * this) / 100
}

fun Float.percentage(per: Float): Float {
    return (per * this) / 100
}

fun Array<Float>.floatArrayToIntArray(): IntArray {
    val intArray = IntArray(this.size)
    for (i in this.indices) {
        intArray[i] = this[i].toInt()
    }
    return intArray
}

const val TAG = "AZChart"

fun String.log() = Log.e(TAG,this)
fun Float.log() = Log.e(TAG,this.toString())
fun Int.log() = Log.e(TAG,this.toString())

