package com.icare.mvvm.transform

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.icare.mvvm.base.BaseApp
import java.security.MessageDigest

class CropCircleWithBorderTransformation(
    val borderSize: Int = 0,
    val borderColor: Int = Color.TRANSPARENT
) : BitmapTransformation() {

    private val id = "${BaseApp.content!!.packageName}.CropCircleWithBorderTransformation"

    override fun transform(
        context: Context,
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val bitmap = TransformationUtils.circleCrop(pool, toTransform, outWidth, outHeight)
        setCanvasBitmapDensity(toTransform, bitmap)

        val paint = Paint().apply {
            color = borderColor
            style = Paint.Style.STROKE
            strokeWidth = borderSize.toFloat()
            isAntiAlias = true
        }

        val canvas = Canvas(bitmap)
        canvas.drawCircle(
            outWidth / 2f,
            outHeight / 2f,
            outWidth.coerceAtLeast(outHeight) / 2f - borderSize / 2f,
            paint
        )
        return bitmap
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(
            (id + borderSize + borderColor).toByteArray(
                Key.CHARSET
            )
        )
    }

    override fun equals(other: Any?): Boolean {
        return other is CropCircleWithBorderTransformation &&
                other.borderSize == borderSize &&
                other.borderColor == borderColor
    }

    override fun hashCode(): Int {
        return id.hashCode() + borderSize * 100 + borderColor + 10
    }
}