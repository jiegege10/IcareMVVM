package com.icare.mvvm.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import com.icare.mvvm.R
import com.qmuiteam.qmui.widget.dialog.QMUIBaseDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialog

abstract class BaseBottomDialog(mContext: Context) : QMUIBaseDialog(
    mContext, R.style.QMUI_Dialog
) {
    protected abstract fun init()
    protected abstract fun getLayout(): Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        init()
    }

    override fun onStop() {
        super.onStop()
        dismiss()
    }

    init {
        // 拿到Dialog的Window, 修改Window的属性
        val window = window
        window!!.setWindowAnimations(R.style.PopAnimation)
        window.decorView.setPadding(0, 0, 0, 0)
        // 获取Window的LayoutParams
        val attributes = window.attributes
        attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
        attributes.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        // 一定要重新设置, 才能生效
        window.attributes = attributes
    }
}