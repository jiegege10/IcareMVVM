package com.icare.demo

import androidx.lifecycle.MutableLiveData
import com.icare.jetpackmvvm.base.viewmodel.BaseViewModel
import com.icare.jetpackmvvm.ext.request
import com.icare.jetpackmvvm.state.ResultState


/**
  *
  * @author:         Mr.He
  * @createDate:     6/17/21 3:01 PM
  * @updateUser:     更新者：Mr.He
  * @updateDate:     6/17/21 3:01 PM
 */
class RequestAriticleViewModel : BaseViewModel() {


    //首页轮播图数据
    var bannerData: MutableLiveData<ResultState<Any>> = MutableLiveData()


    fun getShareData() {
        request(
            { apiService.getBanner() }//请求体
            , bannerData,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
            true,//是否显示等待框，，默认false不显示 可以默认不传
            "请求中..."//等待框内容，可以默认不填请求网络中...
        )
    }

}