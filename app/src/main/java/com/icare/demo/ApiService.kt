package com.icare.demo

import retrofit2.http.*

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/23
 * 描述　: 网络API
 */
interface ApiService {

    companion object {
        const val SERVER_URL = "http://dev.tianxiangyuan-main.zaihukeji.cn/api/"
        const val SERVER_URL1 = "https://wanandroid.com/"
    }


    /**
     * 获取banner数据
     */
    @POST("common/sendSms")
    suspend fun getBanner(): ApiResponse<Any>



}