package com.icare.mvvm.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.icare.mvvm.R
import com.qmuiteam.qmui.widget.dialog.QMUIBaseDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialog

abstract class BaseDialog(mContext: Context) : QMUIBaseDialog(
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
        window?.setWindowAnimations(R.style.PopCenteredAnimation)

    }
}