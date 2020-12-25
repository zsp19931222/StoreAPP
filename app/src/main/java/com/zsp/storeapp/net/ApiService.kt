package com.zsp.storeapp.net

import com.zsp.storeapp.base.BaseEntity
import com.zsp.storeapp.base.ListResultEntity
import com.zsp.storeapp.entity.BannerResult
import com.zsp.storeapp.entity.RaidersEntity
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * description: api
 * author:created by Andy on 2020/12/25 0025 10:51
 * email:zsp872126510@gmail.com
 */
interface ApiService {
    @GET("banner/getBannerList")
    suspend fun getBannerList(): BaseEntity<ListResultEntity<BannerResult>>

    @GET("raider/getRaiderList/{pageSize}/{pageNum}")
    suspend fun getRaiderList(@Path(value = "pageSize") pageSize:Int , @Path(value = "pageNum") pageNum:Int ): BaseEntity<ListResultEntity<RaidersEntity>>
}