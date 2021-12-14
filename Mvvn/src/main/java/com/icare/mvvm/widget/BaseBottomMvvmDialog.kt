package com.icare.mvvm.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.icare.mvvm.R
import com.qmuiteam.qmui.widget.dialog.QMUIDialog

abstract class BaseBottomMvvmDialog< DB : ViewDataBinding>(mContext: Context) : QMUIDialog(
    mContext, R.style.color_dialog
) {
    lateinit var mDatabind: DB
    protected abstract fun init()
    protected abstract fun getLayout(): Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDatabind = DataBindingUtil.inflate( LayoutInflater.from(context), getLayout(), null, false)
        setContentView(mDatabind.root)
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