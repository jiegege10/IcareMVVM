package com.icare.jetpackmvvm.callback.databind

import androidx.databinding.ObservableField


/**
 *
 * @description:     自定义的Boolean类型ObservableField 提供了默认值，避免取值的时候还要判空
 * @author:         Mr.He
 * @createDate:     6/17/21 11:11 AM
 * @updateUser:     更新者：Mr.He
 * @updateDate:     6/17/21 11:11 AM
 */
class BooleanObservableField(value: Boolean = false) : ObservableField<Boolean>(value) {
    override fun get(): Boolean {
        return super.get()!!
    }

}