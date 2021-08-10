package com.icare.demo

import com.icare.mvvm.base.BaseApp

/**
 * +----------------------------------------------------------------------
 * | zaihukeji [ WE CAN DO IT MORE SIMPLE]
 * +----------------------------------------------------------------------
 * | Copyright (c) 2016-2021 http://icarexm.com/ All rights reserved.
 * +----------------------------------------------------------------------
 * | Author: Mr.He    <email：450951286@qq.com>
 * +----------------------------------------------------------------------
 *
 * @description:     类作用描述
 * @createDate:     2021/7/19
 * @updateUser:     更新者：Mr.He
 * @updateDate:     2021/7/19
 * @updateRemark:   更新说明：
 * @version:        1.0
 **/
class AA : BaseApp() {
    override fun onCreate() {
        super.onCreate()

//        OkHttpFinal.getInstance().init(OkHttpFinalConfiguration.Builder().build())
        preferenceName = "123123"
    }
}