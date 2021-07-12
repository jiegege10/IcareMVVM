package com.icare.demo

import retrofit2.http.*

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/23
 * 描述　: 网络API
 */
interface ApiService {

    companion object {
        const val SERVER_URL = "https://shengbaishop.zaihukeji.cn/api/"
    }

    @GET("cookbook/category")
    suspend fun getBanner(@Query("search") search: String = ""): ApiResponse<Data>

}