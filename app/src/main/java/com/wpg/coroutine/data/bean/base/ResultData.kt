package com.wpg.coroutine.data.bean.base


/**
 *
 * @Author:        xuwei
 * @Date:          2021/3/16 9:32
 * @Description:
 */
sealed class ResultData<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultData<T>()
    data class Error(val exception: Exception) : ResultData<Nothing>()
    data class ErrorMessage(val message: String) : ResultData<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data = $data]"
            is Error -> "Error[exception = $exception]"
            is ErrorMessage -> message
        }
    }
}