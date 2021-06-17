package com.icare.jetpackmvvm.callback.livedata

import androidx.lifecycle.MutableLiveData


/**
 *
 * @description:     自定义的Short类型 MutableLiveData 提供了默认值，避免取值的时候还要判空
 * @author:         Mr.He
 * @createDate:     6/17/21 11:14 AM
 * @updateUser:     更新者：Mr.He
 * @updateDate:     6/17/21 11:14 AM
 */
class ShortLiveData : MutableLiveData<Short>() {
    override fun getValue(): Short {
        return super.getValue() ?: 0
    }
}