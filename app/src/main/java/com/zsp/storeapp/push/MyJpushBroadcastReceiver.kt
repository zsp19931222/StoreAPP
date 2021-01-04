package com.zsp.storeapp.push

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import cn.jpush.android.api.JPushInterface
import com.google.gson.JsonObject
import com.zsp.storeapp.activity.WebActivity
import com.zsp.storeapp.util.IsNullUtil
import me.andy.mvvmhabit.util.ZLog
import org.json.JSONException
import org.json.JSONObject

/**
 * description:
 * author:created by Andy on 2019/7/2 0002 11:12
 * email:zsp872126510@gmail.com
 */
class MyJpushBroadcastReceiver : BroadcastReceiver() {
    private var href = ""
    override fun onReceive(context: Context, intent: Intent) {
        try {
            val bundle = intent.extras
            ZLog.d(
                "[MyReceiver] onReceive - " + intent.action + ", extras: " + printBundle(
                    bundle
                )
            )
            if (JPushInterface.ACTION_REGISTRATION_ID == intent.action) {
                val regId = bundle!!.getString(JPushInterface.EXTRA_REGISTRATION_ID)
                ZLog.d("[MyReceiver] 接收Registration Id : $regId")
                //send the Registration Id to your server...
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action) {
                ZLog.d("[MyReceiver] 接收到推送下来的自定义消息: " + bundle!!.getString(JPushInterface.EXTRA_MESSAGE))
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action) {
                ZLog.d("[MyReceiver] 接收到推送下来的通知")
                val notifactionId = bundle!!.getInt(JPushInterface.EXTRA_NOTIFICATION_ID)
                ZLog.d("[MyReceiver] 接收到推送下来的通知的ID: $notifactionId")
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action) {
                ZLog.d("[MyReceiver] 用户点击打开了通知")
                if (!IsNullUtil.getInstance().isEmpty(href)) {
                    val intent = Intent(context, WebActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra("href", href)
                    ZLog.d(href)
                    context.startActivity(intent)
                }
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK == intent.action) {
                ZLog.d("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle!!.getString(JPushInterface.EXTRA_EXTRA))
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
            } else if (JPushInterface.ACTION_CONNECTION_CHANGE == intent.action) {
                val connected =
                    intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false)
                //                ZLog.w( "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
            } else {
                ZLog.d("[MyReceiver] Unhandled intent - " + intent.action)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ZLog.e(e.toString())
        }
    }

    // 打印所有的 intent extra 数据
    private fun printBundle(bundle: Bundle?): String {
        val sb = StringBuilder()
        for (key in bundle!!.keySet()) {
            if (key == JPushInterface.EXTRA_NOTIFICATION_ID) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getInt(key))
            } else if (key == JPushInterface.EXTRA_CONNECTION_CHANGE) {
                sb.append("\nkey:").append(key).append(", value:").append(bundle.getBoolean(key))
            } else if (key == JPushInterface.EXTRA_EXTRA) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    ZLog.i("This message has no Extra data")
                    continue
                }
                try {
                    val json = JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA))
                    val it = json.keys()
                    while (it.hasNext()) {
                        val myKey = it.next()
                        if ("href" == myKey) {
                            href = json.optString(myKey)
                        }
                        sb.append("\nkey:").append(key).append(", value: [").append(myKey)
                            .append(" - ").append(json.optString(myKey)).append("]")
                    }
                } catch (e: JSONException) {
                    ZLog.e("Get message extra JSON error!")
                }
            } else {
                sb.append("\nkey:").append(key).append(", value:").append(bundle[key])
            }
        }
        return sb.toString()
    }
}