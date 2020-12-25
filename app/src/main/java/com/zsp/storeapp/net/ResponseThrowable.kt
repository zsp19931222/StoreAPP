package com.zsp.storeapp.net

/**
 * description:
 * author:created by Andy on 2020/12/25 0025 15:35
 * email:zsp872126510@gmail.com
 */
class ResponseThrowable(throwable: Throwable?, code: Int) :
    Exception(throwable) {
    var code: Int = code
    override var message: String? = null

}