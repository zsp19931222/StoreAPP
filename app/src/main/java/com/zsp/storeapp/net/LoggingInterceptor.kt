package com.zsp.storeapp.net

import android.annotation.SuppressLint
import com.luck.picture.lib.tools.SPUtils
import me.andy.mvvmhabit.util.ZLog
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

/**
 * description:网络拦截器动态改变baseURL等
 * author:created by Andy on 2020/12/25 0025 14:43
 * email:zsp872126510@gmail.com
 */
class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        var request = chain.request()

        //从request中获取原有的HttpUrl实例oldHttpUrl
        val oldHttpUrl = request.url
        //获取request的创建者builder
        val token: String? = try {
            SPUtils.getInstance().getString("token")
        } catch (e: Exception) {
            ""
        }
        ZLog.d("token", token)
        val builder = request.newBuilder()
            .addHeader("token", token!!)
        request = builder.url(oldHttpUrl).build()
        return getResponse(request, chain)
    }

    /**
     * 拦截请求并打印log
     */
    @SuppressLint("DefaultLocale")
    @Throws(IOException::class)
    private fun getResponse(
        request: Request,
        chain: Interceptor.Chain
    ): Response {
        val t1 = System.nanoTime() //请求发起的时间
        val response = chain.proceed(request)
        val t2 = System.nanoTime() //收到响应的时间

        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        val responseBody = response.peekBody(1024 * 1024.toLong())
        ZLog.d(
            String.format(
                "请求连接: [%s] %n%.1fms%n%s",
                response.request.url,
                (t2 - t1) / 1e6,
                response.headers
            )
        )
        val responseBodyJson =
            responseBody.string() //发现第一调用responseBody.string()有数据返回，而第二次却返回NULL
        ZLog.json(response.request.url.toString(), responseBodyJson)
        return response
    }
}