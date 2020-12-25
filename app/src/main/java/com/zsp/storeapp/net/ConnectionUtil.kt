package com.zsp.storeapp.net

import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import com.luck.picture.lib.tools.ToastUtils
import me.andy.mvvmhabit.base.BaseApplication
import me.andy.mvvmhabit.util.ZLog
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

/**
 * description:网络连接成功失败处理
 * author:created by Andy on 2020/12/25 0025 14:45
 * email:zsp872126510@gmail.com
 */
class ConnectionUtil {

    companion object {
        const val SUCCESS = "200"
        private const val UNAUTHORIZED = 401
        private const val FORBIDDEN = 403
        private const val NOT_FOUND = 404
        private const val REQUEST_TIMEOUT = 408
        private const val INTERNAL_SERVER_ERROR = 500
        private const val SERVICE_UNAVAILABLE = 503
        fun handleException(e: Throwable) {
            ZLog.e(e.toString())
            val ex: ResponseThrowable
            if (e is HttpException) {
                ex = ResponseThrowable(e, ERROR.HTTP_ERROR)
                when (e.code()) {
                    UNAUTHORIZED -> ex.message = "操作未授权"
                    FORBIDDEN -> ex.message = "请求被拒绝"
                    NOT_FOUND -> ex.message = "资源不存在"
                    REQUEST_TIMEOUT -> ex.message = "服务器执行超时"
                    INTERNAL_SERVER_ERROR -> ex.message = "服务器内部错误"
                    SERVICE_UNAVAILABLE -> ex.message = "服务器不可用"
                    else -> ex.message = "网络错误"
                }
            } else if (e is JsonParseException
                || e is JSONException
                || e is android.net.ParseException || e is MalformedJsonException
            ) {
                ex = ResponseThrowable(e, ERROR.PARSE_ERROR)
                ex.message = "解析错误"
            } else if (e is ConnectException) {
                ex = ResponseThrowable(e, ERROR.NETWORD_ERROR)
                ex.message = "连接失败"
            } else if (e is SSLException) {
                ex = ResponseThrowable(e, ERROR.SSL_ERROR)
                ex.message = "证书验证失败"
            } else if (e is ConnectTimeoutException) {
                ex = ResponseThrowable(e, ERROR.TIMEOUT_ERROR)
                ex.message = "连接超时"
            } else if (e is SocketTimeoutException) {
                ex = ResponseThrowable(e, ERROR.TIMEOUT_ERROR)
                ex.message = "连接超时"
            } else if (e is UnknownHostException) {
                ex = ResponseThrowable(e, ERROR.TIMEOUT_ERROR)
                ex.message = "主机地址未知"
            } else {
                ex = ResponseThrowable(e, ERROR.UNKNOWN)
                ex.message = "未知错误"
            }
            ToastUtils.s(BaseApplication.getContext(), ex.message)
        }

    }

    /**
     * 约定异常 这个具体规则需要与服务端或者领导商讨定义
     */
    internal object ERROR {
        /**
         * 未知错误
         */
        const val UNKNOWN = 1000

        /**
         * 解析错误
         */
        const val PARSE_ERROR = 1001

        /**
         * 网络错误
         */
        const val NETWORD_ERROR = 1002

        /**
         * 协议出错
         */
        const val HTTP_ERROR = 1003

        /**
         * 证书出错
         */
        const val SSL_ERROR = 1005

        /**
         * 连接超时
         */
        const val TIMEOUT_ERROR = 1006
    }

}