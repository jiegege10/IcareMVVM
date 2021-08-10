package com.icare.mvvm.callback.databind

import androidx.databinding.ObservableField
/**
 *
 * @description:     自定义的Int类型 ObservableField  提供了默认值，避免取值的时候还要判空
 * @author:         Mr.He
 * @createDate:     6/17/21 11:12 AM
 * @updateUser:     更新者：Mr.He
 * @updateDate:     6/17/21 11:12 AM
 */

class DoubleObservableField(value: Double = 0.0) : ObservableField<Double>(value) {

    override fun get(): Double {
        return super.get()!!
    }

}