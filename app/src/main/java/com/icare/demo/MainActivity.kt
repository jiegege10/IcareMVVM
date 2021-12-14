package com.icare.demo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.icare.demo.databinding.ActivityMainBinding
import com.icare.demo.databinding.ActivityMainBindingImpl
import com.icare.mvvm.base.activity.BaseVmActivity
import com.icare.mvvm.base.viewmodel.BaseViewModel
import com.icare.mvvm.util.CommonUtil
import com.luck.picture.lib.tools.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseVmActivity<BaseViewModel>() {


    override fun layoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        findViewById<TextView>(R.id.btn).setOnClickListener {
            showProgressDialog("ssss")
        }
    }

    override fun showLoading(message: String) {
    }

    override fun dismissLoading() {
    }

    override fun createObserver() {
    }

    override fun tokenExpiredObserver(message: String) {
    }


}