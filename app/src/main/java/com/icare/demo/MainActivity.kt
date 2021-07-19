package com.icare.demo

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.activity.viewModels
import com.google.gson.Gson
import com.icare.demo.databinding.ActivityMainBinding
import com.icare.jetpackmvvm.base.activity.BaseVmActivity
import com.icare.jetpackmvvm.ext.parseState
import com.icare.jetpackmvvm.ext.util.toJson
import com.icare.jetpackmvvm.network.manager.NetState
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<RequestAriticleViewModel, ActivityMainBinding>() {
    //请求数据ViewModel
    private val requestHomeViewModel: RequestAriticleViewModel by viewModels()
    override fun layoutId(): Int = R.layout.activity_main
    override fun initView(savedInstanceState: Bundle?) {
        addLoadingObserve(requestHomeViewModel)
        tvtitle.setLeftOnClickListener {
            requestHomeViewModel.getShareData()
        }

    }
   
    override fun createObserver() {
        super.createObserver()
        requestHomeViewModel.bannerData.observe(this) {
            parseState(it, {
                Log.d("XXXXXXXX", Gson().toJson(it))
            })
        }
    }


}