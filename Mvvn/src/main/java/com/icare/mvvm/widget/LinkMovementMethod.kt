package com.icare.mvvm.widget

import android.graphics.Color
import android.text.Layout
import android.text.Selection
import android.text.Spannable
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.widget.TextView

class LinkMovementMethod : LinkMovementMethod() {
    override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
        val action: Int = event.getAction()
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            var x = event.getX()
            var y = event.getY()
            x -= widget.totalPaddingLeft
            y -= widget.totalPaddingTop
            x += widget.scrollX
            y += widget.scrollY
            val layout: Layout = widget.layout
            val line: Int = layout.getLineForVertical(y.toInt())
            val off: Int = layout.getOffsetForHorizontal(line, x)
            val link: Array<ClickableSpan> = buffer.getSpans(off, off, ClickableSpan::class.java)
            if (link.isNotEmpty()) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(widget)
                    buffer.setSpan(
                        BackgroundColorSpan(Color.TRANSPARENT),
                        buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    Selection.removeSelection(buffer)
                } else if (action == MotionEvent.ACTION_DOWN) {
                    buffer.setSpan(
                        BackgroundColorSpan(Color.GRAY),
                        buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    Selection.setSelection(
                        buffer,
                        buffer.getSpanStart(link[0]),
                        buffer.getSpanEnd(link[0])
                    )
                } else if (action == MotionEvent.ACTION_MOVE) {
                    buffer.setSpan(
                        BackgroundColorSpan(Color.TRANSPARENT),
                        buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    Selection.removeSelection(buffer)
                }
                return true
            } else {
                Selection.removeSelection(buffer)
            }
        }
        //        return super.onTouchEvent(widget, buffer, event);
        return false
    }

    companion object {
        private var sInstance: LinkMovementMethod? = null
        val instance: MovementMethod?
            get() {
                if (sInstance == null) sInstance = LinkMovementMethod()
                return sInstance
            }
    }
}