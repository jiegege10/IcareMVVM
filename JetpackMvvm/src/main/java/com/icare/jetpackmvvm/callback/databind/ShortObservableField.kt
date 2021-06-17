package com.icare.jetpackmvvm.callback.databind

import androidx.databinding.ObservableField


/**
 *
 * @description:     自定义的 Short 类型 ObservableField  提供了默认值，避免取值的时候还要判空
 * @author:         Mr.He
 * @createDate:     6/17/21 11:13 AM
 * @updateUser:     更新者：Mr.He
 * @updateDate:     6/17/21 11:13 AM
 */
class ShortObservableField(value: Short = 0) : ObservableField<Short>(value) {

    override fun get(): Short {
        return super.get()!!
    }

}