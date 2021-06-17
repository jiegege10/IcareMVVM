package com.icare.jetpackmvvm.callback.livedata

import androidx.lifecycle.MutableLiveData


/**
 *
 * @description:     类自定义的Boolean类型 MutableLiveData 提供了默认值，避免取值的时候还要判空作用描述
 * @author:         Mr.He
 * @createDate:     6/17/21 11:13 AM
 * @updateUser:     更新者：Mr.He
 * @updateDate:     6/17/21 11:13 AM
 */
class BooleanLiveData : MutableLiveData<Boolean>() {

    override fun getValue(): Boolean {
        return super.getValue() ?: false
    }
}

