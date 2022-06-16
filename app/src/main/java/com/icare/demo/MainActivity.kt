package com.icare.demo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.icare.demo.databinding.ActivityMainBinding
import com.icare.mvvm.base.activity.BaseVmActivity
import com.icare.mvvm.base.viewmodel.BaseViewModel
import com.icare.mvvm.ext.util.logd
import com.icare.mvvm.util.CommonUtil
import com.icare.mvvm.util.SharedFlowBus
import com.luck.picture.lib.tools.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}