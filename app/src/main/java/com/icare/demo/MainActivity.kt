package com.icare.demo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.gson.Gson
import com.icare.demo.a.TestDialog
import com.icare.demo.databinding.ActivityMainBinding
import com.icare.mvvm.ext.init
import com.icare.mvvm.ext.parseState
import com.icare.mvvm.ext.setSingleClickListener
import com.icare.mvvm.util.Preference
import kotlinx.android.synthetic.main.activity_main.*

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
            var dialog = TestDialog(this)
            dialog.show()
//                requestHomeViewModel.getShareData()
//            showLoading()
//            }catch (e:Exception){
//                Log.e("XXXXXXXXX",e.toString())
//            }
//            HttpRequest.download(this,"https://app.pcpsoo.com//uploads//shops//20210322//8530ea5f14cfa8d82d5342057bb3b575.jpg",
//                File("/sdcard/xx.png"),
//                object :FileDownloadCallback(){
//                    override fun onStart() {
//                        super.onStart()
//                    }
//
//                    override fun onDone() {
//                        super.onDone()
//                        LogUtils.debugInfo("下载完成")
//                    }
//
//                    override fun onFailure() {
//                        super.onFailure()
//                    }
//
//                    override fun onProgress(progress: Int, networkSpeed: Long) {
//                        super.onProgress(progress, networkSpeed)
//                        LogUtils.debugInfo("下载中：$progress")
//                    }
//                })
        }
    }

    override fun createObserver() {
        super.createObserver()
        requestHomeViewModel.bannerData.observe(this) {
            parseState(it, {
                Log.d("XXXXXXXX", Gson().toJson(it))
            },{
                showToast(it.errorMsg)
            })
        }
    }


}