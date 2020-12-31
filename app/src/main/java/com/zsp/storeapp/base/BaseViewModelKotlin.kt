package com.zsp.storeapp.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luck.picture.lib.tools.ToastUtils
import com.zsp.storeapp.net.ConnectionUtil
import com.zsp.storeapp.net.ErrorResult
import com.zsp.storeapp.net.HttpUtil
import kotlinx.coroutines.*
import me.andy.mvvmhabit.base.BaseApplication
import me.andy.mvvmhabit.base.BaseModel
import me.andy.mvvmhabit.base.BaseViewModel
import me.andy.mvvmhabit.util.ZLog

/**
 * description:封装了请求
 * author:created by Andy on 2020/12/25 0025 10:51
 * email:zsp872126510@gmail.com
 */
open class BaseViewModelKotlin(application: Application) : BaseViewModel<BaseModel>(application) {
    val httpUtil by lazy { HttpUtil.getInstance() }

    private var errorData = MutableLiveData<ErrorResult>()//错误信息



    private fun showError(error: ErrorResult) {
        errorData.value = error
    }

    private fun error(errorResult: ErrorResult) {
        showError(ErrorResult(errorResult.code, errorResult.message))
    }


    /**
     * 注意此方法传入的参数：api是以函数作为参数传入
     * api：即接口调用方法
     * error：可以理解为接口请求失败回调
     * ->数据类型，表示方法返回该数据类型
     * ->Unit，表示方法不返回数据类型
     */
    fun <T> launch(
        api: suspend CoroutineScope.() -> BaseEntity<T>,//请求接口方法，T表示data实体泛型，调用时可将data对应的bean传入即可
        liveData: MutableLiveData<T>,
        isShowLoading: Boolean = false
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {//异步请求接口
                    val result = api()
                    withContext(Dispatchers.Main) {
                        if (result.code == ConnectionUtil.SUCCESS) {//请求成功
                            liveData.value = result.result
                        } else {
                            error(ErrorResult(result.code, result.message))
                            ToastUtils.s(BaseApplication.getContext(), result.message)
                        }
                    }
                }
            }.onSuccess {//请求成功并结束
            }.onFailure() {//接口请求失败
                if (it is CancellationException) {
                    ZLog.d(it)
                } else {
                    ConnectionUtil.handleException(e = it)
                }
            }
        }
    }
}