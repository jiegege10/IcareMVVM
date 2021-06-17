package com.icare.jetpackmvvm.callback.databind

import androidx.databinding.ObservableField


/**
 *
 * @description:    自定义的 Float 类型 ObservableField  提供了默认值，避免取值的时候还要判空
 * @author:         Mr.He
 * @createDate:     6/17/21 11:12 AM
 * @updateUser:     更新者：Mr.He
 * @updateDate:     6/17/21 11:12 AM
 */
class FloatObservableField(value: Float = 0f) : ObservableField<Float>(value) {

    override fun get(): Float {
        return super.get()!!
    }

}