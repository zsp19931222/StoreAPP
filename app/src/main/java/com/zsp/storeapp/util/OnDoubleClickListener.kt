package com.zsp.storeapp.util

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View


/**
 * description:
 * author:created by Andy on 2021/1/29 0029 15:48
 * email:zsp872126510@gmail.com
 */
open class OnDoubleClickListener(callback: DoubleClickCallback?) :
    View.OnTouchListener {
    private var count = 0 //点击次数
    private var firstClick: Long = 0 //第一次点击时间
    private var secondClick: Long = 0 //第二次点击时间

    /**
     * 两次点击时间间隔，单位毫秒
     */
    private val totalTime = 1000

    /**
     * 自定义回调接口
     */
    private val mCallback: DoubleClickCallback? = callback

    interface DoubleClickCallback {
        fun onDoubleClick()
    }

    /**
     * 触摸事件处理
     * @param v
     * @param event
     * @return
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (MotionEvent.ACTION_DOWN == event.action) { //按下
            count++
            if (1 == count) {
                firstClick = System.currentTimeMillis() //记录第一次点击时间
            } else if (2 == count) {
                secondClick = System.currentTimeMillis() //记录第二次点击时间
                if (secondClick - firstClick < totalTime) { //判断二次点击时间间隔是否在设定的间隔时间之内
                    mCallback?.onDoubleClick()
                    count = 0
                    firstClick = 0
                } else {
                    firstClick = secondClick
                    count = 1
                }
                secondClick = 0
            }
        }
        return false
    }

}