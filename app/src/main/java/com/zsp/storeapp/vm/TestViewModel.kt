package com.zsp.storeapp.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.zsp.storeapp.base.BaseViewModelKotlin
import com.zsp.storeapp.base.ListResultEntity
import com.zsp.storeapp.entity.BannerResult
import com.zsp.storeapp.entity.RaidersEntity
import com.zsp.storeapp.entity.SportsNewsEntity
import kotlinx.coroutines.delay

/**
 * description:
 * author:created by Andy on 2020/12/25 0025 11:45
 * email:zsp872126510@gmail.com
 */
class TestViewModel(application: Application) : BaseViewModelKotlin(application) {
    var bannerData = MutableLiveData<ListResultEntity<BannerResult>>()
    var raidersData = MutableLiveData<ListResultEntity<RaidersEntity>>()
    var sportsList = MutableLiveData<ListResultEntity<SportsNewsEntity>>()
    var state = MutableLiveData<Int>()
    var pageSize = MutableLiveData<Int>()
    var pageNum = MutableLiveData<Int>()
    fun getBanner() {
        launch({ httpUtil.getBanner() }, bannerData)
        launch({ httpUtil.getRaiderList(10, 1) }, raidersData)
        launch(
            { httpUtil.getNewsList(state.value ?: 0, pageSize.value ?: 10, pageNum.value ?: 1) },
            sportsList
        )
    }
}