package com.icare.mvvm.network.manager

import com.icare.mvvm.callback.livedata.event.EventLiveData


/**
 *
 * @description:     网络变化管理者
 * @author:         Mr.He
 * @createDate:     6/17/21 11:20 AM
 * @updateUser:     更新者：Mr.He
 * @updateDate:     6/17/21 11:20 AM
 */
class NetworkStateManager private constructor() {

    val mNetworkStateCallback = EventLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }

}