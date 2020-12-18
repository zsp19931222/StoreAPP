package com.zsp.storeapp;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.zsp.storeapp.adapter.ImageAdapter;
import com.zsp.storeapp.entity.BannerResult;
import com.zsp.storeapp.entity.BaseEntity;
import com.zsp.storeapp.net.DefaultObserver;
import com.zsp.storeapp.net.IdeaApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import me.goldze.mvvmhabit.utils.RxUtils;


/**
 * description:
 * author:created by Andy on 2020/12/1 0001 17:53
 * email:zsp872126510@gmail.com
 */
class Test {
    public void get(){
        ObservableList<BannerResult> resultObservableField=new ObservableArrayList<>();
        List<BannerResult> bannerResults=new ArrayList<>();
        ImageAdapter imageAdapter=new ImageAdapter(resultObservableField);
        IdeaApi.getApiService().getBanner()
                .compose(RxUtils.schedulersTransformer())
                .
              subscribe(new DefaultObserver<BaseEntity<BannerResult>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(BaseEntity<BannerResult> response) {

            }
        });
    }
}
