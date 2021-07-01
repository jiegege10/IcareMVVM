package com.icare.demo

import com.icare.jetpackmvvm.network.BaseResponse

data class ApiResponse<T>(val code: Int, val msg: String, val data: T) : BaseResponse<T>() {

    // 这里是示例，wanandroid 网站返回的 错误码为 0 就代表请求成功，请你根据自己的业务需求来改变
    override fun isSuccess() = code == 200

    override fun getResponseCode() = code

    override fun getResponseData() = data

    override fun getResponseMsg() = msg
    override fun isLoginError(): Boolean = code == 6002
}