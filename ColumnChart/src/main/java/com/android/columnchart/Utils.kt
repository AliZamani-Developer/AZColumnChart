package com.android.columnchart

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt



fun adjustAlpha(@ColorInt color: Int, factor: Float): Int {
    val alpha = Math.round(Color.alpha(color) * factor).toInt()
    val red: Int = Color.red(color)
    val green: Int = Color.green(color)
    val blue: Int = Color.blue(color)
    return Color.argb(alpha, red, green, blue)
}

fun drawableToBitmap(drawable: Drawable): Bitmap {
    var bitmap: Bitmap? = null
    if (drawable is BitmapDrawable) {
        if (drawable.bitmap != null) {
            return drawable.bitmap
        }
    }
    bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
        Bitmap.createBitmap(
            1,
            1,
            Bitmap.Config.ARGB_8888
        )
    } else {
        Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
    }
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun findLargestTextLength(strings: List<String>?): String {
    var maxLengthText = ""
    for (string in strings!!) {
        if (string.length > maxLengthText.length) {
            maxLengthText = string
        }
    }
    return maxLengthText
}

fun findMostFrequentNumber(numbers: IntArray): Int {
    val map = mutableMapOf<Int, Int>()
    var maxNumber = numbers[0]
    var maxCount = 1

    for (number in numbers) {
        val count = map[number]?.plus(1) ?: 1
        map[number] = count

        if (count > maxCount) {
            maxNumber = number
            maxCount = count
        }
    }

    return maxNumber
}

