package com.zsp.storeapp.net;

/**
 * author：Andy on 2019/3/14 0014 17:31
 * email：zsp872126510@gmail.com
 * 请求放回状态码
 */

public class RequestCodeUtil {
    public static final String USER_AUTHENTICATION_FAILURE_CODE = "401";//token失效
    public static final String SUCCESS = "200";
    public static final String HTTP_TAG = "HTTP_TAG";

    //网络相关
    public final static String PARSE_ERROR = "解析数据失败";
    public final static String BAD_NETWORK = "网络异常";
    public final static String CONNECT_ERROR = "服务器连接异常";
    public final static String CONNECT_TIMEOUT = "连接超时";
    public final static String UNKNOWN_ERROR = "未知错误";
}
