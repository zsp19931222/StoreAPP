package com.zsp.storeapp.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.zsp.storeapp.base.BaseViewModelKotlin
import com.zsp.storeapp.base.ListResultEntity
import com.zsp.storeapp.entity.SportsVideoEntity

/**
 * description:
 * author:created by Andy on 2021/1/8 0008 16:29
 * email:zsp872126510@gmail.com
 */
class VideoViewModel(application: Application) : BaseViewModelKotlin(application) {
    var videoList = MutableLiveData<ListResultEntity<SportsVideoEntity>>()
    var pageSize = MutableLiveData<Int>()
    var pageNum = MutableLiveData<Int>()
    fun getData() {
        launch(
            { httpUtil.getVideoList(pageSize.value ?: 10, pageNum.value ?: 1) },
            videoList
        )
    }
}