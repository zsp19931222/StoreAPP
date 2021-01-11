package com.zsp.storeapp;

import android.content.Context;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.zsp.storeapp.entity.SportsNewsEntity;

import java.util.ArrayList;
import java.util.List;

import kotlinx.coroutines.Job;
import me.andy.mvvmhabit.bus.RxBus;
import me.andy.mvvmhabit.util.ZLog;

/**
 * description:
 * author:created by Andy on 2020/12/25 0025 17:10
 * email:zsp872126510@gmail.com
 */
class Test {
    private Job job;
    List<SportsNewsEntity> list=new ArrayList<>();
    void text(){
        for (int i = 0; i < 10; i++) {

        }
    }
}
