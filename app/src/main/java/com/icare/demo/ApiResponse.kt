package com.icare.demo

import com.icare.jetpackmvvm.network.BaseResponse

data class ApiResponse<T>(val errorCode: Int, val errorMsg: String, val data: T) : BaseResponse<T>() {

    // 这里是示例，wanandroid 网站返回的 错误码为 0 就代表请求成功，请你根据自己的业务需求来改变
    override fun isSucces() = errorCode == 0

    override fun getResponseCode() = errorCode

    override fun getResponseData() = data

    override fun getResponseMsg() = errorMsg

}