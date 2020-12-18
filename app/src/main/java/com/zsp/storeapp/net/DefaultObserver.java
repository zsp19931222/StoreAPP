package com.zsp.storeapp.net;

import android.content.Intent;

import com.google.gson.JsonParseException;
import com.zsp.storeapp.activity.LoginRegisterActivity;
import com.zsp.storeapp.entity.BaseEntity;
import com.zsp.storeapp.util.ToastUtil;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import me.goldze.mvvmhabit.base.BaseApplication;
import me.goldze.mvvmhabit.utils.KLog;
import retrofit2.HttpException;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.zsp.storeapp.net.RequestCodeUtil.SUCCESS;
import static com.zsp.storeapp.net.RequestCodeUtil.USER_AUTHENTICATION_FAILURE_CODE;


/**
 * 请求集中处理类
 *
 * @author Andy
 * created at 2019/3/14 0014 18:03
 */

public abstract class DefaultObserver<T extends BaseEntity> implements Observer<T> {


    public DefaultObserver() {
    }


    @Override
    public void onNext(T response) {
        KLog.d(response);
        boolean isSuccess;

        //是否获取数据成功
        isSuccess = response.getCode().equals(SUCCESS);

        if (isSuccess) {
            onSuccess(response);
        } else {
            onFail(response);
        }
    }


    @Override
    public void onError(Throwable e) {
        KLog.e(e.toString());
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    /**
     * 服务器返回数据，但响应码不为40001
     *
     * @param response 服务器返回的数据
     */
    public void onFail(T response) {
        if (response.getCode().equals(USER_AUTHENTICATION_FAILURE_CODE)){
            Intent intent=new Intent(BaseApplication.getInstance().getApplicationContext(), LoginRegisterActivity.class);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            BaseApplication.getInstance().getApplicationContext().startActivity(intent);
        }else {
            ToastUtil.normalToast(BaseApplication.getInstance().getApplicationContext(), response.getMessage());
        }
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        ToastUtil.normalToast(BaseApplication.getInstance().getApplicationContext(), setErrorText(reason));
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }

    public String setErrorText(DefaultObserver.ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                return RequestCodeUtil.CONNECT_ERROR;
            case CONNECT_TIMEOUT:
                return RequestCodeUtil.CONNECT_TIMEOUT;

            case BAD_NETWORK:
                return RequestCodeUtil.BAD_NETWORK;

            case PARSE_ERROR:
                return RequestCodeUtil.PARSE_ERROR;
            case UNKNOWN_ERROR:
                return RequestCodeUtil.UNKNOWN_ERROR;
        }
        return "";
    }
}
