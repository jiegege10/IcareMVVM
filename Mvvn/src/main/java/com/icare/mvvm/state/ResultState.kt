package com.icare.mvvm.state
import androidx.lifecycle.MutableLiveData
import com.icare.mvvm.network.AppException
import com.icare.mvvm.network.BaseResponse
import com.icare.mvvm.network.ExceptionHandle

/**
* @date: 2021/7/20 6:01 下午
* @author: Mr.He
* @param 自定义结果集封装类
* @return
*/
sealed class ResultState<out T> {
    companion object {
        fun <T> onAppSuccess(data: T): ResultState<T> = Success(data)
        fun <T> onAppLoading(loadingMessage:String): ResultState<T> = Loading(loadingMessage)
        fun <T> onAppError(error: AppException): ResultState<T> = Error(error)
    }
    data class Loading(val loadingMessage:String) : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val error: AppException) : ResultState<Nothing>()
}

/**
 * 处理返回值
 * @param result 请求结果
 */
fun <T> MutableLiveData<ResultState<T>>.paresResult(result: BaseResponse<T>) {
    value = when {
        result.isSuccess() -> {
            ResultState.onAppSuccess(result.getResponseData())
        }
        else -> {

            ResultState.onAppError(AppException(result.getResponseCode(), result.getResponseMsg()))
        }
    }
}

/**
 * 不处理返回值 直接返回请求结果
 * @param result 请求结果
 */
fun <T> MutableLiveData<ResultState<T>>.paresResult(result: T) {
    value = ResultState.onAppSuccess(result)
}

/**
 * 异常转换异常处理
 */
fun <T> MutableLiveData<ResultState<T>>.paresException(e: Throwable) {
    this.value = ResultState.onAppError(ExceptionHandle.handleException(e))
}

