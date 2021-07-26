package com.icare.demo

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.activity.viewModels
import com.google.gson.Gson
import com.hjq.permissions.OnPermissionCallback
import com.icare.demo.databinding.ActivityMainBinding
import com.icare.jetpackmvvm.base.BaseApp
import com.icare.jetpackmvvm.base.activity.BaseVmActivity
import com.icare.jetpackmvvm.ext.init
import com.icare.jetpackmvvm.ext.parseState
import com.icare.jetpackmvvm.ext.setSingleClickListener
import com.icare.jetpackmvvm.ext.util.toJson
import com.icare.jetpackmvvm.network.manager.NetState
import com.icare.jetpackmvvm.util.LogUtils
import com.icare.jetpackmvvm.util.Preference
import com.icare.jetpackmvvm.util.download.FileDownloadCallback
import com.icare.jetpackmvvm.util.download.HttpRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<RequestAriticleViewModel, ActivityMainBinding>() {
    //请求数据ViewModel
    private var AAA: Boolean by Preference("Constant.SP_GOOGLE2FA_SECRET", false)
    private val requestHomeViewModel: RequestAriticleViewModel by viewModels()
    override fun layoutId(): Int = R.layout.activity_main
    override fun initView(savedInstanceState: Bundle?) {
        addLoadingObserve(requestHomeViewModel)
        tvtitle.setLeftOnClickListener {


        }
        tvtitle.setRightIcon(R.mipmap.icon_delete_gray)
        tvtitle.setRightIconVisibility(true)
        tvtitle.init("", this)
        mDatabind.tv.setSingleClickListener {
//            try {
//                requestHomeViewModel.getShareData()
//            }catch (e:Exception){
//                Log.e("XXXXXXXXX",e.toString())
//            }
            HttpRequest.download(this,"https://app.pcpsoo.com//uploads//shops//20210322//8530ea5f14cfa8d82d5342057bb3b575.jpg",
                File("/sdcard/xx.png"),
                object :FileDownloadCallback(){
                    override fun onStart() {
                        super.onStart()
                    }

                    override fun onDone() {
                        super.onDone()
                        LogUtils.debugInfo("下载完成")
                    }

                    override fun onFailure() {
                        super.onFailure()
                    }

                    override fun onProgress(progress: Int, networkSpeed: Long) {
                        super.onProgress(progress, networkSpeed)
                        LogUtils.debugInfo("下载中：$progress")
                    }
                })
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