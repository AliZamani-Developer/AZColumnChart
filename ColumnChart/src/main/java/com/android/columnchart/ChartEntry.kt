package com.android.columnchart

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

data class ChartEntry(
    val value: Float,
    @ColorInt val color:Int?=null,
    @ColorInt val startColor:Int?=null,
    @ColorInt val endColor:Int?=null,
    @DrawableRes val icon: Int?=null,
)

