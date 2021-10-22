package com.icare.mvvm.widget

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
/**
  *
  * @description:     类作用描述
  * @author:         Mr.He
  * @createDate:     2021/9/2 9:18 上午
  * @updateUser:     更新者：Mr.He
  * @updateDate:     2021/9/2 9:18 上午
 */
class CustomPopupWindow(var builder: Builder) {
    private val mPopupWindow: PopupWindow?
    private val contentView: View =
        LayoutInflater.from(mContext).inflate(builder.contentViewId, null)

    /**
     * popup 消失
     */
    fun dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing) {
            mPopupWindow.dismiss()
        }
    }

    fun setOnDismissListener(ll: PopupWindow.OnDismissListener) {
        mPopupWindow?.setOnDismissListener {
            ll.onDismiss()
        }
    }

    /**
     * 相对于窗体的显示位置
     *
     * @param view 可以为Activity中的任意一个View（最终的效果一样），
     * 会通过这个View找到其父Window，也就是Activity的Window。
     * @param gravity 在窗体中的位置，默认为Gravity.NO_GRAVITY
     * @param x 表示距离Window边缘的距离，方向由Gravity决定。
     * 例如：设置了Gravity.TOP，则y表示与Window上边缘的距离；
     * 而如果设置了Gravity.BOTTOM，则y表示与下边缘的距离。
     * @param y
     * @return
     */
    fun showAtLocation(view: View?, gravity: Int, x: Int, y: Int): CustomPopupWindow {
        mPopupWindow?.showAtLocation(view, gravity, x, y)
        return this
    }

    /**
     * 显示在anchor控件的正下方，或者相对这个控件的位置
     *
     * @param anchor
     * @param xoff
     * @param yoff
     * @param gravity
     * @return
     */
    fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int): CustomPopupWindow {
        mPopupWindow?.showAsDropDown(anchor, xoff, yoff, gravity)
        return this
    }

    /**
     * 根据id获取view
     *
     * @param viewId
     * @return
     */
    fun getItemView(viewId: Int): View? {
        return if (mPopupWindow != null) {
            contentView.findViewById(viewId)
        } else null
    }

    /**
     * 根据id设置pop内部的控件的点击事件的监听
     *
     * @param viewId
     * @param listener
     */
    fun setOnClickListener(viewId: Int, listener: View.OnClickListener?) {
        val view: View? = getItemView(viewId)
        view?.setOnClickListener(listener)
    }

    /**
     * builder 类
     */
    class Builder(context: Context?) {
        var contentViewId //pop的布局文件
                = 0
        var width //pop的宽度
                = 0
        var height //pop的高度
                = 0
        var animStyle //动画效果
                = 0

        fun setContentView(contentViewId: Int): Builder {
            this.contentViewId = contentViewId
            return this
        }

        fun setWidth(width: Int): Builder {
            this.width = width
            return this
        }

        fun setHeight(height: Int): Builder {
            this.height = height
            return this
        }

        fun setAnimationStyle(animStyle: Int): Builder {
            this.animStyle = animStyle
            return this
        }

        fun build(): CustomPopupWindow {
            return CustomPopupWindow(this)
        }

        init {
            mContext = context
        }
    }

    companion object {
        private var mContext: Context? = null
    }

    init {
        mPopupWindow = PopupWindow(contentView, builder.width, builder.height)
        //设置点击外部可以取消，必须和下面这个方法配合才有效
        mPopupWindow.isOutsideTouchable = true
        //设置一个空背景,设置了这个背景之后，设置点击外部取消才有效
        mPopupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //Popupwindow可以点击,PopupWindow弹出后，所有的触屏和物理按键都有PopupWindows处理。
        // 其他任何事件的响应都必须发生在PopupWindow消失之后， （home  等系统层面的事件除外）。
        // 比如这样一个PopupWindow出现的时候，按back键首先是让PopupWindow消失，
        // 第二次按才是退出activity，
        mPopupWindow.isFocusable = true
        mPopupWindow.animationStyle = builder.animStyle //设置pop显示的动画效果
    }
}