package com.icare.jetpackmvvm.base.viewmodel

import androidx.lifecycle.ViewModel
import com.icare.jetpackmvvm.callback.livedata.event.EventLiveData


/**
 *
 * @description:     ViewModel的基类 使用ViewModel类，放弃AndroidViewModel，原因：用处不大 完全有其他方式获取Application上下文
 * @author:         Mr.He
 * @createDate:     6/17/21 11:11 AM
 * @updateUser:     更新者：Mr.He
 * @updateDate:     6/17/21 11:11 AM
 */
open class BaseViewModel : ViewModel() {

    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    /**
     * 内置封装好的可通知Activity/fragment 显示隐藏加载框 因为需要跟网络请求显示隐藏loading配套才加的，不然我加他个鸡儿加
     */
    inner class UiLoadingChange {
        //显示加载框
        val showDialog by lazy { EventLiveData<String>() }
        //隐藏
        val dismissDialog by lazy { EventLiveData<Boolean>() }

        val toast by lazy { EventLiveData<String>() }
    }

}