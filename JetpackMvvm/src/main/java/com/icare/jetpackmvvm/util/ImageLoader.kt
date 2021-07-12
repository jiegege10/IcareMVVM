package com.icare.jetpackmvvm.util

import android.graphics.Color
import android.widget.ImageView
import androidx.annotation.ColorInt
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.icare.jetpackmvvm.transform.CropCircleWithBorderTransformation
import com.icare.jetpackmvvm.transform.RoundedCornersTransformation

/**
 * 加载原始大小的图片
 */
fun ImageView.loadCropImage(src: Any?, placeholder: Int? = null, error: Int? = null) {
    if (src == null) return
    Glide.with(this).asDrawable().centerCrop().load(src).also {
        if (placeholder != null) it.placeholder(placeholder)
        if (error != null) it.error(error)
    }.into(this)
}

/**
 * 加载适应大小的图片
 */
fun ImageView.loadCenterImage(src: Any?, placeholder: Int? = null, error: Int? = null) {
    if (src == null) return
    Glide.with(this).asDrawable().fitCenter().load(src).also {
        if (placeholder != null) it.placeholder(placeholder)
        if (error != null) it.error(error)
    }.into(this)
}

fun ImageView.loadImage(src: Any) {
    if (src == null) return
    Glide.with(this).asDrawable().fitCenter().load(src).into(this)
}

/**
 * 加载gif
 */
fun ImageView.loadGif(src: Any?, placeholder: Int? = null, error: Int? = null) {
    if (src == null) return
    Glide.with(this).asGif().centerInside().load(src).also {
        if (placeholder != null) it.placeholder(placeholder)
        if (error != null) it.error(error)
    }.into(this)
}

/**
 * 加载圆形图片
 * @param borderSize 圆形边的宽度(默认为0)
 * @param borderColor 圆形边的颜色(默认颜色为透明)
 */
fun ImageView.loadCircleImage(
    src: Any?,
    borderSize: Int = 0,
    @ColorInt borderColor: Int = Color.TRANSPARENT,
    placeholder: Int? = null, error: Int? = null,
) {
    if (src == null) return
    Glide.with(this).asDrawable().transform(
        CropCircleWithBorderTransformation(borderSize.px, borderColor)
    ).load(src).also {
        if (placeholder != null) it.placeholder(placeholder)
        if (error != null) it.error(error)
    }.into(this)
}

/**
 * 加载圆角图片
 * @param radius 圆角的半径(默认圆角为6dp)
 * @param cornerType 需要设置圆角的类型(默认四个角全部圆角)
 */
fun ImageView.loadRoundImage(
    src: Any?,
    radius: Int = 6,
    cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL,
    placeholder: Int? = null, error: Int? = null,
) {
    if (src == null) return
    Glide.with(this).asBitmap().transform(
        MultiTransformation(CenterCrop(), RoundedCornersTransformation(radius.px, 0, cornerType))
    ).also {
        if (placeholder != null) it.placeholder(placeholder)
        if (error != null) it.error(error)
    }.load(src).into(this)
}
