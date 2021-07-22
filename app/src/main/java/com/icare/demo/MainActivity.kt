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
import com.icare.jetpackmvvm.util.Preference
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
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
            try {
                requestHomeViewModel.getShareData()
            }catch (e:Exception){
                Log.e("XXXXXXXXX",e.toString())
            }
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