package com.zsp.storeapp.net

import com.zsp.storeapp.base.BaseEntity
import com.zsp.storeapp.base.ListResultEntity
import com.zsp.storeapp.entity.SportsNewsEntity

/**
 * description:封装接口调用工具类
 * author:created by Andy on 2020/12/25 0025 10:49
 * email:zsp872126510@gmail.com
 */
class HttpUtil {

    private val mService by lazy { RetrofitClient.getInstance().create() }

    suspend fun getBanner() = mService.getBannerList()

    suspend fun getRaiderList(pageSize: Int, pageNum: Int) =
        mService.getRaiderList(pageSize, pageNum)

    suspend fun getNewsList(state: Int, pageSize: Int, pageNum: Int) =
        mService.getNewsList(state, pageSize, pageNum)

    /**
     * 单例模式
     */
    companion object {
        @Volatile
        private var httpUtil: HttpUtil? = null

        fun getInstance() = httpUtil ?: synchronized(this) {
            httpUtil ?: HttpUtil().also { httpUtil = it }
        }
    }

}
