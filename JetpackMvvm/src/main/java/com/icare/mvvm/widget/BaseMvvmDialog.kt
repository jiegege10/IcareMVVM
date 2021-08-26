package com.icare.mvvm.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.icare.mvvm.R

abstract class BaseMvvmDialog< DB : ViewDataBinding>(mContext: Context) : Dialog(
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
}