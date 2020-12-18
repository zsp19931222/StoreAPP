package com.zsp.storeapp.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.zsp.storeapp.net.ApiService.DEFAULT_TIMEOUT;
import static com.zsp.storeapp.net.ApiService.SERVER_ADDRESS;

/**
 * Created by zsp on 2017/4/1.
 * 网络
 */

public class IdeaApi {
    private Retrofit retrofit;
    private ApiService service;

    private IdeaApi() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggingInterceptor())
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(SERVER_ADDRESS)
                .build();
        service = retrofit.create(ApiService.class);
    }

    //  创建单例
    private static class SingletonHolder {
        private static final IdeaApi INSTANCE = new IdeaApi();
    }

    public static synchronized ApiService getApiService() {
        return SingletonHolder.INSTANCE.service;
    }

}
