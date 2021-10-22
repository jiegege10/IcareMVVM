package com.icare.mvvm.callback.livedata

import androidx.lifecycle.MutableLiveData

/**
 *
 * @description:     自定义的Double类型 MutableLiveData 提供了默认值，避免取值的时候还要判空
 * @author:         Mr.He
 * @createDate:     6/17/21 11:13 AM
 * @updateUser:     更新者：Mr.He
 * @updateDate:     6/17/21 11:13 AM
 */
class DoubleLiveData : MutableLiveData<Double>() {
    override fun getValue(): Double {
        return super.getValue() ?: 0.0
    }
}