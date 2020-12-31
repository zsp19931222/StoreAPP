package com.zsp.storeapp.push

import android.content.Context
import android.os.Handler
import cn.jpush.android.api.JPushInterface
import cn.jpush.android.api.JPushMessage
import cn.jpush.android.service.JPushMessageReceiver
import com.luck.picture.lib.tools.SPUtils
import me.andy.mvvmhabit.util.ZLog

/**
 * description:注册监听
 * author:created by Andy on 2019/7/2 0002 11:16
 * email:zsp872126510@gmail.com
 */
class MyJpushMessageReceiver : JPushMessageReceiver() {
    //alias 相关的操作会在此方法中回调结果。
    override fun onAliasOperatorResult(
        context: Context,
        jPushMessage: JPushMessage
    ) {
        ZLog.d("jpush返回码：" + jPushMessage.errorCode + "--------getSequence：" + jPushMessage.sequence)
        val setAliasUtil = SetAliasUtil(context)
        if (jPushMessage.sequence == 10086) { //注册别名
            if (jPushMessage.errorCode == 0) {
                ZLog.d("别名注册成功：" + jPushMessage.alias)
                ZLog.d("RegistrationID：" + JPushInterface.getRegistrationID(context))
            } else {
                ZLog.d("别名注册失败，失败信息：" + jPushMessage.errorCode)
                //注册别名失败后20秒在进行注册
                Handler().postDelayed({
                    setAliasUtil.setAlias(
                        SPUtils.getInstance().getString("setAlias")
                    )
                }, 10000)
            }
        } else if (jPushMessage.sequence == 10087) { //删除别名
            if (jPushMessage.errorCode == 0) {
                ZLog.d("别名删除成功：" + jPushMessage.alias)
            } else {
                ZLog.d("别名删除失败，失败信息：" + jPushMessage.errorCode)
                //注册别名失败后20秒在进行注册
                Handler()
                    .postDelayed({ setAliasUtil.deleteAlias() }, 10000)
            }
        }
    }

    //查询某个 tag 与当前用户的绑定状态的操作会在此方法中回调结果。
    override fun onCheckTagOperatorResult(
        context: Context,
        jPushMessage: JPushMessage
    ) {
        super.onCheckTagOperatorResult(context, jPushMessage)
    }

    //设置手机号码会在此方法中回调结果。
    override fun onMobileNumberOperatorResult(
        context: Context,
        jPushMessage: JPushMessage
    ) {
        super.onMobileNumberOperatorResult(context, jPushMessage)
    }

    //tag 增删查改的操作会在此方法中回调结果。
    override fun onTagOperatorResult(
        context: Context,
        jPushMessage: JPushMessage
    ) {
    }
}