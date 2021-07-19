package com.icare.demo

import retrofit2.http.*

interface ApiService {

    companion object {
        const val SERVER_URL = "https://shengbaishop.zaihukeji.cn/api/"
    }

    @GET("cookbook/category")
    suspend fun getBanner(@Query("search") search: String = ""): ApiResponse<Data>

}