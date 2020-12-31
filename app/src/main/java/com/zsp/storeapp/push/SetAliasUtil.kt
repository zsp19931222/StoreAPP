package com.zsp.storeapp.push

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Message
import cn.jpush.android.api.JPushInterface
import com.luck.picture.lib.tools.SPUtils
import com.zsp.storeapp.util.IsNullUtil
import me.andy.mvvmhabit.util.ZLog

/**
 * description:注册别名与删除别名
 * author:created by Andy on 2019/7/2 0002 11:18
 * email:zsp872126510@gmail.com
 */
class SetAliasUtil(private val context: Context) : ISetAlias {

    /**
     * 设置别名
     */
    override fun setAlias(alias: String?) {
        if (IsNullUtil.getInstance().isEmpty(alias)) return
        SPUtils.getInstance().put("setAlias", alias)
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias))
    }

    /**
     * 删除别名
     */
    override fun deleteAlias() {
        JPushInterface.deleteAlias(context, 10087)
    }

    /**
     * @param context
     * @param setDebugMode 是否开启极光debug模式
     * @description:
     * @author: Andy
     * @date: 2019/7/2 0002 15:37
     */
    override fun initJPush(
        context: Context?,
        setDebugMode: Boolean
    ) {
        JPushInterface.setDebugMode(setDebugMode)
        JPushInterface.init(context)
    }

    @SuppressLint("HandlerLeak")
    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_SET_ALIAS -> {
                    ZLog.d("Set alias in handler.")
                    // 调用 JPush 接口来设置别名。
                    //激光推送用户自定义的操作序列号，同操作结果一起返回，用来标识一次操作的唯一性。
                    JPushInterface.setAlias(context, 10086, msg.obj as String)
                }
                else -> ZLog.d("Unhandled msg - " + msg.what)
            }
        }
    }

    /**
     * description: 根据别名删除jpush的绑定设备
     * author: Andy
     * date: 2020/4/16 0016 9:35
     */
    override fun deleteAliasNet() {}

    /**
     * description: 获取MetaData信息
     * author: Andy
     * date: 2020/4/16 0016 10:14
     */
    private fun getMetaDataValue(
        context: Context,
        name: String
    ): String {
        var value: Any? = null
        val packageManager = context.packageManager
        val applicationInfo: ApplicationInfo
        try {
            applicationInfo = packageManager.getApplicationInfo(
                context.packageName, PackageManager.GET_META_DATA
            )
            if (applicationInfo.metaData != null) {
                value = applicationInfo.metaData[name]
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException(
                "Could not read the name in the manifest file.", e
            )
        }
        if (value == null) {
            throw RuntimeException(
                "The name '" + name
                        + "' is not defined in the manifest file's meta data."
            )
        }
        return value.toString()
    }

    companion object {
        private const val MSG_SET_ALIAS = 1001
    }

}