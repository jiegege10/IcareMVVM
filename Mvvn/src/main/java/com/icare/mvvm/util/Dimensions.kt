package com.icare.mvvm.util

import android.util.TypedValue
import com.icare.mvvm.base.BaseApp


val Int.px: Int
    get() = (BaseApp.content!!.resources.displayMetrics.density * this + 0.5F).toInt()

val Float.px: Float
    get() = BaseApp.content!!.resources.displayMetrics.density * this + 0.5F

val Int.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), BaseApp.content!!.resources.displayMetrics
    ).toInt()

val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this, BaseApp.content!!.resources.displayMetrics
    )

val Int.sp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this.toFloat(), BaseApp.content!!.resources.displayMetrics
    ).toInt()

val Float.sp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this, BaseApp.content!!.resources.displayMetrics
    )

val screenWidth: Int = BaseApp.content!!.resources.displayMetrics.widthPixels

val screenHeight: Int = BaseApp.content!!.resources.displayMetrics.heightPixels