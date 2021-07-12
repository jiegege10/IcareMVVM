package com.icare.jetpackmvvm.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.view.View
import com.icare.jetpackmvvm.R
import com.icare.jetpackmvvm.util.CommonUtil
import kotlin.math.roundToInt

class SpaceItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {

    private var mDivider: Drawable? = null
    private val mSectionOffsetV: Int = 0
    private val mSectionOffsetH: Int = 0
    private var mDrawOver = true
    private var color = CommonUtil.getColor(context,R.color.app_dividing)
    private var attrs: IntArray = intArrayOf(android.R.attr.listDivider)

    init {
        var a = context.obtainStyledAttributes(attrs)
        mDivider = a.getDrawable(0)
        a.recycle()
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (mDivider != null && mDrawOver) {
            draw(c, parent)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (mDivider != null && mDrawOver) {
            draw(c, parent)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (getOrientation(parent.layoutManager!!) == RecyclerView.VERTICAL) {
            outRect.set(mSectionOffsetH, 0, mSectionOffsetH, mSectionOffsetV)
        } else {
            outRect.set(0, 0, mSectionOffsetV, 0)
        }
    }

    private fun draw(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin + ViewCompat.getTranslationY(child)
                .roundToInt()
            val bottom = top + if (mDivider!!.intrinsicHeight <= 0) CommonUtil.dp2px(context ,1f) else mDivider!!.intrinsicHeight

            mDivider?.let {
                it.setColorFilter(color, PorterDuff.Mode.XOR)
                it.setBounds(left, top, right, bottom)
                it.draw(c)
            }
        }
    }

    private fun getOrientation(layoutManager: RecyclerView.LayoutManager): Int {
        if (layoutManager is LinearLayoutManager) {
            return layoutManager.orientation
        } else if (layoutManager is StaggeredGridLayoutManager) {
            return layoutManager.orientation
        }
        return OrientationHelper.HORIZONTAL
    }
}