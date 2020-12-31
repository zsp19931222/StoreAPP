package com.zsp.storeapp.vm

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.zsp.storeapp.base.BaseViewModelKotlin
import com.zsp.storeapp.base.ListResultEntity
import com.zsp.storeapp.entity.SportsNewsEntity
import me.andy.mvvmhabit.util.ZLog

/**
 * description:
 * author:created by Andy on 2020/12/29 0029 17:36
 * email:zsp872126510@gmail.com
 */
class FragMainViewModel(application: Application) : BaseViewModelKotlin(application) {
    var sportsList = MutableLiveData<ListResultEntity<SportsNewsEntity>>()
    var state = MutableLiveData<Int>()
    var pageSize = MutableLiveData<Int>()
    var pageNum = MutableLiveData<Int>()

    fun getData() {
        launch(
            { httpUtil.getNewsList(state.value ?: 0, pageSize.value ?: 10, pageNum.value ?: 1) },
            sportsList
        )
    }

}