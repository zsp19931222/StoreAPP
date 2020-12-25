package com.zsp.storeapp.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.zsp.storeapp.base.BaseViewModelKotlin
import com.zsp.storeapp.base.ListResultEntity
import com.zsp.storeapp.entity.BannerResult
import com.zsp.storeapp.entity.RaidersEntity
import kotlinx.coroutines.delay

/**
 * description:
 * author:created by Andy on 2020/12/25 0025 11:45
 * email:zsp872126510@gmail.com
 */
class TestViewModel(application: Application) : BaseViewModelKotlin(application) {
    var bannerData = MutableLiveData<ListResultEntity<BannerResult>>()
    var raidersData = MutableLiveData<ListResultEntity<RaidersEntity>>()

    fun getBanner() {
        launch({ httpUtil.getBanner() }, bannerData)
        launch({ httpUtil.getRaiderList(10, 1) }, raidersData)
    }
}