package com.zsp.storeapp.net;

import android.annotation.SuppressLint;

import com.luck.picture.lib.tools.SPUtils;
import com.zsp.storeapp.util.IsNullUtil;

import java.io.IOException;
import java.util.List;

import me.goldze.mvvmhabit.utils.KLog;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by Administrator on 2018/12/4 0004.
 * 网络拦截器动态改变baseURL等
 */

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();

        //从request中获取原有的HttpUrl实例oldHttpUrl
        HttpUrl oldHttpUrl = request.url();
        //获取request的创建者builder
        String token;
        try {
            token = SPUtils.getInstance().getString("token");
        } catch (Exception e) {
            token = "";
        }
        KLog.d("token", token);
        Request.Builder builder = request.newBuilder()
                .addHeader("token", token);
        request=builder.url(oldHttpUrl).build();
        return getResponse(request, chain);
    }

    /**
     * 拦截请求并打印log
     */
    @SuppressLint("DefaultLocale")
    private Response getResponse(Request request, Chain chain) throws IOException {
        long t1 = System.nanoTime();//请求发起的时间
        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        KLog.d(String.format("请求连接: [%s] %n%.1fms%n%s",
                response.request().url(),
                (t2 - t1) / 1e6d,
                response.headers()));
        String responseBodyJson = responseBody.string();//发现第一调用responseBody.string()有数据返回，而第二次却返回NULL
        KLog.json(response.request().url().toString(), responseBodyJson);
        return response;
    }

}