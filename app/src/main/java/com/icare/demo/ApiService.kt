package com.icare.demo

import retrofit2.http.*

interface ApiService {

    companion object {
        const val SERVER_URL = "http://dev-yipingkaiyuan."
        const val REGISTER = "auth/register" //注册
        const val SEND_SMS = "auth/sendSms" //发送验证码
    }

    @GET("cookbook/category")
    suspend fun getBanner(@Query("search") search: String = ""): ApiResponse<Data>

    @FormUrlEncoded
    @POST(SEND_SMS)
    suspend fun sendSMS(@FieldMap map: MutableMap<String,Any>): ApiResponse<CodeEntity>
}