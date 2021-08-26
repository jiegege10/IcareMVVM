package com.icare.mvvm.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.icare.mvvm.R

abstract class BaseDialog(mContext: Context) : Dialog(
    mContext, R.style.color_dialog
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
}