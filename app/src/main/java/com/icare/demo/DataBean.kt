package com.icare.demo

import android.annotation.SuppressLint
import android.os.Parcelable

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
 * @createDate:     6/17/21
 * @updateUser:     更新者：Mr.He
 * @updateDate:     6/17/21
 * @updateRemark:   更新说明：
 * @version:        1.0
 **/


data class CityEntity(
    val code: String,
    val id: Int,
    val name: String
)
data class BannerResponse(
    var desc: String = "",
    var id: Int = 0,
    var imagePath: String = "",
    var isVisible: Int = 0,
    var order: Int = 0,
    var title: String = "",
    var type: Int = 0,
    var url: String = ""
)


data class TestEntity(
    val `data`: Data
)

data class Data(
    val hot: List<Hot>,
    val list: List<Goods>
)

data class Hot(
    val id: String,
    val image: String,
    val name: String
)

data class Goods(
    val id: String,
    val list: List<X>,
    val name: String
)

data class X(
    val id: String,
    val image: String,
    val name: String
)
