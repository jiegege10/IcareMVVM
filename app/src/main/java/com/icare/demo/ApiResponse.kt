package com.icare.demo

import com.icare.jetpackmvvm.network.BaseResponse

data class ApiResponse<T>(val code: Int, val msg: String, val data: T) : BaseResponse<T>() {

    override fun isSuccess() = code == 2000

    override fun getResponseCode() = code

    override fun getResponseData() = data

    override fun getResponseMsg() = msg
    override fun isLoginError(): Boolean = code == 6002 ||code == 6004
}